package tabladesimbolos;

import interfaz.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import tabladesimbolos.declaraciones.DeclArray;
import tabladesimbolos.declaraciones.DeclConst;
import tabladesimbolos.declaraciones.DeclFuncProc;
import tabladesimbolos.declaraciones.DeclFuncProcDesconocidas;
import tabladesimbolos.declaraciones.DeclParam;
import tabladesimbolos.declaraciones.DeclVar;
import tabladesimbolos.declaraciones.Declaraciones;
import tabladesimbolos.declaraciones.TipoDeclaracion;
import analizador.lexico.tokens.ID;
import analizador.lexico.tokens.TokenTypes;
import exceptions.SemanticException;
import exceptions.TableException;
import exceptions.TypeException;

/**Clase de la tabla de simbolos
 * @author Santy */
public class TablaDeSimbolos implements iTablaDeSimbolos{
	private String contexto;
	private Tipo tipoPendiente;
	
	private TablaID<Declaraciones> tablaGlobal;
	private TablaID<DeclFuncProc> tablaFuncProc;
	private TablaID<DeclFuncProcDesconocidas> tablaFuncProcDesconocidas;
	
	private TablaPalabrasReservadas t_palabras;
	private HashMap<String, TablaID<Declaraciones>> tablasContexto;
	private boolean errorMode = false;
	
	/** Constructor */
	public TablaDeSimbolos(){
		this.contexto = "global";
		this.t_palabras = new TablaPalabrasReservadas();
		this.tablaGlobal = new TablaID<Declaraciones>();
		this.tablaFuncProc = new TablaID<DeclFuncProc>();
		this.tablasContexto = new HashMap<String, TablaID<Declaraciones>>();
		this.tablaFuncProcDesconocidas = new TablaID<DeclFuncProcDesconocidas>();
	}
	
	/** Busca en todas las tablas si existe */
	public boolean existeIDGlobal(String s){
		return this.tablaGlobal.existeID(s);
	}
	
	/** Busca en el contexto y sino en el Global 
	 * @throws TableException */
	public boolean declaredID(String contexto,String s) throws TableException{
		if(contexto == null || contexto.equalsIgnoreCase("global")){
			return this.existeIDGlobal(s);
		}else{
			if(this.tablasContexto.containsKey(contexto)){
				TablaID<Declaraciones> tabla = this.tablasContexto.get(contexto);
				if(tabla.existeID(s)){
					return true;
				}else{
					if(this.tablaGlobal.existeID(s)){
						return true;
					}
					return false;
				}
			}else{
				throw new TableException("El contexto "+contexto+" no existe");
			}
		}
	}
	
	/** Busca en el contexto y sino en el Global 
	 * @throws TableException */
	public boolean declaredID(String s) throws TableException{
		return this.declaredID(this.getContexto(), s);
	}
	
	
	/** Busca en Func 
	 * @throws TableException */
	public boolean declaredFunc(String s){
		if(this.tablaFuncProc.existeID(s)){
			return true;
		}else{
			return false;
		}
	}
	/** Busca si existe como Palabra reservada o como tipo y retorna de que tipo es.
	 * O null si no lo encuentra*/
	public TokenTypes searchPalabraReservada(String palabra){
		TokenTypes tipo = null;
		if(this.t_palabras.existPalabraReservada(palabra)){
			tipo = TokenTypes.PALABRARESERVADA;
		}else{
			if(this.t_palabras.existTipo(palabra)){
				tipo = TokenTypes.TIPO;
			}
		}
		return tipo;
	}
	
	@Override
	public void setContexto(String context) {
		this.contexto=context;
		if(!this.tablasContexto.containsKey(context)){ //cuando hago el cambio de contexto creo la tabla
			this.tablasContexto.put(context, new TablaID<Declaraciones>(context));
		}
	}

	@Override
	public String getContexto() {
		return this.contexto;
	}
	
	public boolean hasMain(){
		return (this.tablaFuncProc.existeID("main") || this.tablaFuncProc.existeID("MAIN") || this.tablaFuncProc.existeID("Main"));
	}

	@Override
	public boolean addVarGlobales(String id, Tipo tipo) throws SemanticException {
		if(this.tablaGlobal.existeID(id)){
			throw new SemanticException("Error declarando la Variable Global: "+contexto,"La Variable "+id+" ya fue declarada");
		}else{
			this.tablaGlobal.add(id, new DeclVar(id, tipo));
			return true;
		}
	}

	@Override
	public boolean addConstGlobales(String id, Tipo tipo, String valor) throws SemanticException {
		if(this.tablaGlobal.existeID(id)){
			throw new SemanticException("Error declarando la Constante Global: "+contexto,"La Constante "+id+" ya fue declarada");
		}else{
			this.tablaGlobal.add(id, new DeclConst(id, tipo, valor));
			return true;
		}
	}

	@Override
	public boolean addArray(String id, Tipo tipo, int tamanio) throws SemanticException{
		if(this.tablaGlobal.existeID(id)){
			throw new SemanticException("Error declarando el Array Global: "+id,"El Array "+id+" ya fue declarado");
		}else{
			this.tablaGlobal.add(id, new DeclArray(id, tipo, tamanio));
			return true;
		}
	}

	@Override
	public boolean addFuncGlobales(String id, ArrayList<Tipo> param, Tipo retorno) throws SemanticException {
		if(this.tablaFuncProc.existeID(id)){
			throw new SemanticException("Error declarando la Funcion : "+id,"La Funcion "+id+" ya fue declarada");
		}else{
			this.tablaFuncProc.add(id, new DeclFuncProc(TipoDeclaracion.FUNC,id, param, retorno));
			this.contexto = id;
			return true;
		}
	}

	@Override
	public boolean addProcGlobales(String id, ArrayList<Tipo> param) throws SemanticException {
		if(this.tablaFuncProc.existeID(id)){
			throw new SemanticException("Error declarando El Procedimiento : "+id,"El Procedimiento "+id+" ya fue declarada");
		}else{
			this.tablaFuncProc.add(id, new DeclFuncProc(TipoDeclaracion.PROC, id, param));
			this.contexto = id;
			return true;
		}
	}
	
	public boolean addVar(String id, Tipo tipo) throws SemanticException{
		return this.addVar(id, tipo, this.contexto);
	}

	@Override
	public boolean addVar(String id, Tipo tipo, String contexto) throws SemanticException {
		if(contexto == null || contexto.equalsIgnoreCase("global")){
			return this.addVarGlobales(id, tipo);
		}else{
			if(!this.tablasContexto.containsKey(contexto)){
				this.tablasContexto.put(contexto, new TablaID<Declaraciones>(contexto));
			}
			if(this.tablasContexto.get(contexto).existeID(id)){
				throw new SemanticException("Error declarando una Variable en la funcion: "+contexto,"La Variable "+id+" ya fue declarado");
			}else{
				this.tablasContexto.get(contexto).add(id,new DeclVar(id,tipo));
				return true;
			}
		}
	}
	
	public boolean addParam(String id, Tipo tipo, boolean byref) throws TableException, SemanticException{
		return this.addParam(id, tipo, byref, this.getContexto());
	}
	
	public boolean addParam(String id, Tipo tipo, boolean byref ,String contexto) throws SemanticException, TableException {
		if(contexto == null || contexto.equalsIgnoreCase("global")){
			throw new TableException("Error en la tabla de simbolos","No se puede insertar un Parametro en el ambito global");
		}else{
			if(!this.tablasContexto.containsKey(contexto)){
				this.tablasContexto.put(contexto, new TablaID<Declaraciones>(contexto));
			}
			if(this.tablasContexto.get(contexto).existeID(id)){
				throw new SemanticException("Error declarando un parametro en la funcion: "+contexto,"El parametro "+id+" ya fue declarado");
			}else{
				this.tablasContexto.get(contexto).add(id,new DeclParam(id,tipo,byref));
				return true;
			}
		}
	}
	
	public boolean addConst(String id, Tipo tipo, String valor)throws SemanticException{
		return this.addConst(id, tipo, valor, this.getContexto());
	}
	
	public boolean addConst(String id, Tipo tipo, String valor, String contexto)throws SemanticException{
		if(contexto == null || contexto.equalsIgnoreCase("global")){
			return this.addConstGlobales(id, tipo, valor);
		}else{
			if(!this.tablasContexto.containsKey(contexto)){
				this.tablasContexto.put(contexto, new TablaID<Declaraciones>(contexto));
			}
			if(this.tablasContexto.get(contexto).existeID(id)){
				throw new SemanticException("Error declarando una Constante en la funcion: "+contexto,"La Constante "+id+" ya fue declarado");
			}else{
				this.tablasContexto.get(contexto).add(id,new DeclConst(id,tipo,valor));
				return true;
			}
		}
	}
	
	public boolean addFunc(String id, ArrayList<Tipo> param, Tipo retorno)throws SemanticException{
		return this.addFunc(id, param, retorno, this.getContexto());
	}
	public boolean addFunc(String id, ArrayList<Tipo> param, Tipo retorno, String contexto)throws SemanticException{
		return this.addFuncGlobales(id, param, retorno);
	}
	public boolean addProc(String id, ArrayList<Tipo> param)throws SemanticException{
		return this.addProc(id, param, this.getContexto());
	}
	public boolean addProc(String id, ArrayList<Tipo> param, String contexto)throws SemanticException{
		return this.addProcGlobales(id, param);
	}

	@Override
	public void setContextoGlobal() {
		this.contexto = null;
		
	}

	@Override
	public boolean isGlobalContext() {
		if(this.contexto==null || this.contexto.equalsIgnoreCase("global")){
			return true;
		}
		return false;
	}
	
	
	
	public void printTabla(){
		UI.informar("Mostrando Tablas:");
		UI.informar("Global: ");
		this.tablaGlobal.printTable();
		UI.informar("-------------------------------------------------------------");
		UI.informar("Funciones y Procedimiento: ");
		this.tablaFuncProc.printTable();
		UI.informar("-------------------------------------------------------------");
		Iterator<TablaID<Declaraciones>> it = this.tablasContexto.values().iterator();
		while(it.hasNext()){
			it.next().printTable();
		}
	}

	@Override
	public boolean checkParam(String id,ArrayList<Tipo> params) throws TypeException {
		DeclFuncProc func = this.tablaFuncProc.get(id);
		if(func==null){			
				throw new TypeException("La funcion "+id+" no se encuentra declarada");			
		}else{
			if(func.getParametros() != null && params != null){
				if(func.getParametros().size() == params.size()){
					Iterator<Tipo> it1 = func.getParametros().iterator();
					Iterator<Tipo> it2 = params.iterator();
					int i = 1;
					while(it1.hasNext()){
						if(it1.next() != it2.next()){
							throw new TypeException("Error en el pasaje de parametros de la funcion: "+id, "El tipo del parametro " + i + "no coincide con el declarado por la funcion " + id);
						}
						i++;
					}
					return true;
				}else{
					throw new TypeException("Error en el pasaje de parametros de la funcion: "+id, "La cantidad de parametros no coinciden");
				}
			}else{
				if(((func.getParametros()==null)&& (params == null || params.size()==0)) || ((params==null)&& (func.getParametros() == null || func.getParametros().size()==0))){
					return true;
				}else{
					throw new TypeException("Error en el pasaje de parametros de la funcion: "+id, "No se pasaron parametros o se pasaron parametros a una funcion que no tiene");
				}
			}
		}
	}
	
	public Tipo getTipoFuncProc(String id) throws TableException{
		if(this.tablaFuncProc.existeID(id)){			
			DeclFuncProc aux = this.tablaFuncProc.get(id);
			return aux.getTipo();
		}else{
			throw new TableException("Error buscando en la tabla de simbolos","La variable "+id+"no esta declarada como una funcion");
		}
	}
	
	public Tipo getTipoGlobal(String id) throws TableException{
		if(this.tablaGlobal.existeID(id)){
			Declaraciones aux = this.tablaGlobal.get(id);
			return aux.getTipo();
		}else{
			throw new TableException("Error buscando en la tabla de simbolos","La variable "+id+"no esta declarada en el contexto global");
		}
	}
	
	public Tipo getTipo(String contexto,String id) throws TableException{
		if(contexto==null || contexto.equalsIgnoreCase("global")){
			return this.getTipoGlobal(id);
		}else{
			if(this.tablasContexto.containsKey(contexto)){
				TablaID<Declaraciones> table = this.tablasContexto.get(contexto);
				if(table.existeID(id)){
					Declaraciones aux = table.get(id);
					return aux.getTipo();
				}else{
					return this.getTipoGlobal(id);
				}
			}else{
				throw new TableException("Error buscando en la tabla de simbolos","No existe el contexto "+contexto);
			}
		}
	}
	
	public Tipo getTipo(String id) throws TableException{
		return this.getTipo(this.getContexto(), id);
	}
	
	/**@deprecated*/
	public DeclFuncProc getFuncProc(String id) throws TableException{
		if(this.tablaFuncProc.existeID(id)){
			return this.tablaFuncProc.get(id);
		}else{
			throw new TableException("Error buscando en la tabla de simbolos","La variable "+id+"no esta declarada como una funcion");
		}
	}
	
	public Declaraciones getGlobal(String id) throws TableException{
		if(this.tablaGlobal.existeID(id)){
			return this.tablaGlobal.get(id);
		}else{
			throw new TableException("Error buscando en la tabla de simbolos","La variable "+id+"no esta declarada en el contexto global");
		}
	}
	
	public Declaraciones get(String contexto,String id) throws TableException{
		if(contexto==null || contexto.equalsIgnoreCase("global")){
			return this.getGlobal(id);
		}else{
			if(this.tablasContexto.containsKey(contexto)){
				TablaID<Declaraciones> table = this.tablasContexto.get(contexto);
				if(table.existeID(id)){
					return table.get(id);
				}else{
					return this.getGlobal(id);
				}
			}else{
				throw new TableException("Error buscando en la tabla de simbolos","No existe el contexto "+contexto);
			}
		}
	}
	
	public Declaraciones get(String id) throws TableException{
		return this.get(this.getContexto(), id);
	}
	
	public void setErrorMode(){
		this.errorMode = true;
	}
	
	public boolean isErrorMode(){
		return this.errorMode;
	}

	@Override
	public boolean addFuncProcDesconocidas(String id, ArrayList<Tipo> tipo,
			ID idtoken) {		
		DeclFuncProcDesconocidas funcProcDesc = new DeclFuncProcDesconocidas(id,tipo,idtoken);
		this.tablaFuncProcDesconocidas.add(id, funcProcDesc);
		return true;
	}

	@Override
	public boolean addFuncProcDesconocidas(String id, ArrayList<Tipo> tipo,
			ID idtoken, Tipo retorno) {
		DeclFuncProcDesconocidas funcProcDesc = new DeclFuncProcDesconocidas(id,tipo,idtoken, retorno);
		this.tablaFuncProcDesconocidas.add(id, funcProcDesc);
		return true;
	}

	public Tipo getTipoPendiente() {
		return tipoPendiente;
	}

	public void setTipoPendiente(Tipo tipoPendiente) {
		this.tipoPendiente = tipoPendiente;
	}
	
	public Iterator<DeclFuncProcDesconocidas> getIteratorDesconocidas(){
		return this.tablaFuncProcDesconocidas.getIterator();
	}
	
}

