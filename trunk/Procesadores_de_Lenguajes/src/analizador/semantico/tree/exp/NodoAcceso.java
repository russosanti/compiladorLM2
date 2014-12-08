package analizador.semantico.tree.exp;

import exceptions.CodeAlgorithmicError;
import exceptions.CodeException;
import exceptions.TableException;
import generadorCodigo.ParameterRetainer;
import generadorCodigo.TemporalManager;
import tabladesimbolos.SingleTabla;
import tabladesimbolos.iTablaDeSimbolos;
import tabladesimbolos.declaraciones.DeclConst;
import tabladesimbolos.declaraciones.DeclParam;
import tabladesimbolos.declaraciones.Declaraciones;
import analizador.lexico.tokens.ID;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoAcceso extends NodoExp{
	
	protected String id;

	@SuppressWarnings("unused")
	private ID token;
	private TipoNodo tipoNodo = TipoNodo.ACCESO;
	
	public NodoAcceso(String id){
		this. id = id;
	}
	
	public NodoAcceso(String id, ID tkn){
		this(id);
		this.token = tkn;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" ID: "+this.id;
	}
	
	@Override
	public void generateCode() throws CodeException {
		ITree cg = ExecutionTree.getInstance();
		cg.insertCode("MOV AX,"+this.getValor());
		if(this.needsTemporal()){
			TemporalManager tm = TemporalManager.getInstance();
			String temp = tm.getTemporal();
			this.setTemporalUsed(temp);
			cg.insertCode("MOV " + temp +",AX");
		}
	}
	
	public String getValor() throws CodeException{
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		try {
			Declaraciones decl = tabla.get(this.id);
			switch(decl.getTipoDeclaracion()){
			case VAR:
				if(tabla.declaredIDinContext(this.id)){
					return decl.getID()+tabla.getContexto();
				}else{
					return decl.getID();
				}
			case CONST:
				DeclConst dc = (DeclConst)decl;
				return dc.getValor();
			case PARAM:
				DeclParam dp = (DeclParam)decl;
				ParameterRetainer pr = ParameterRetainer.getInstance();
				if(dp.isByRef()){
					ITree cg = ExecutionTree.getInstance();
					cg.insertCode("MOV BX, [bp+" + pr.getParamPos(dp.getID()) +"]");
					return "word ptr [BX]";
				}else{
					return "[bp+" + pr.getParamPos(dp.getID()) +"]";
				}
			default:
				throw new CodeAlgorithmicError("Tipo de declaracion no valida para Nodo Acceso");
			}
		} catch (TableException e) {
			throw new CodeAlgorithmicError(e.getTitle(),e.getMessage());
		} catch (ClassCastException ice){
			throw new CodeAlgorithmicError("Error en el Nodo Acceso",ice.getMessage());
		}
	}

	public TipoNodo getTipoNodo() {
		return tipoNodo;
	}

	public void setTipoNodo(TipoNodo tipoNodo) {
		this.tipoNodo = tipoNodo;
	}
	
	public String getId() {
		return id;
	}
}
