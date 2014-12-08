package analizador.semantico.reglas.exp;

import java.util.Stack;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import tabladesimbolos.iTablaDeSimbolos;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;
import exceptions.TableException;
import exceptions.TypeException;
import analizador.lexico.tokens.Booleano;
import analizador.lexico.tokens.Entero;
import analizador.lexico.tokens.ID;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.INodo.TipoNodo;
import analizador.semantico.tree.exp.HojaBoolean;
import analizador.semantico.tree.exp.HojaInteger;
import analizador.semantico.tree.exp.NodoAcceso;
import analizador.semantico.tree.exp.NodoAccesoArray;
import analizador.semantico.tree.exp.NodoLlamada;
import analizador.sintactico.estructuras.LRApilable;

public class FACT extends EXP implements ReglaSemantica {
	
	public FACT(){
		super(FACT.class.getSimpleName());
	}
	
	public FACT(String s){
		super(s);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) throws SemanticException {
		if(prod!=null){
			try{
				Token t = (Token)prod.get(prod.size()-1); //las 4 producciones arrancan con token
				if(prod.size()==1){
					if(t.getType() == TokenTypes.BOOLEANO){
						return this.accionSemantica((Booleano)t);
					}else{
						return this.accionSemantica((Entero)t);
					}
				}else{
					if(prod.size()==2){
						return this.accionSemantica((ID)t, (ID1)prod.get(0));
					}else{
						if(prod.size()==3){
							return this.accionSemantica((EXP)prod.get(1));
						}else{
							throw new AlgorithmicError("Error algoritmico en la produccion FACT", "La Produccion vino mal formada");
						}
					}
				}
			}catch(ClassCastException e){
				throw new AlgorithmicError("Error algoritmico en la produccion FACT", "La Produccion vino mal formada");
			}catch(IndexOutOfBoundsException ex){
				throw new AlgorithmicError("Error algoritmico en la produccion FACT", ex.getMessage());
			}
		}else{
			throw new AlgorithmicError("Error algoritmico en la produccion FACT", "La Produccion no acepta lambda");
		}
	}

	private boolean accionSemantica(EXP exp) {
		this.setArbol(exp.getArbol());
		this.setTipo(exp.getTipo());
		return true;
	}

	private boolean accionSemantica(ID t, ID1 id1) throws SemanticException {
		iTablaDeSimbolos ts = SingleTabla.getInstance();
		try{
			if(id1.getTipoNodo()==TipoNodo.ACCESO){
				this.setArbol(new NodoAcceso(t.getLexema(),t));
				if(ts.declaredID(t.getLexema())){
					this.setTipo(ts.getTipo(t.getLexema()));
					return true;
				}else{
					throw new SemanticException("Error semantico en "+t.getLexema(),"La variable "+t.getLexema()+" no esta declarada");
				}
			}else{
				if(id1.getTipoNodo()==TipoNodo.ACCESOARRAY){
					this.setArbol(new NodoAccesoArray(t.getLexema(),id1.getArbolExp(),t));
					if(ts.declaredID(t.getLexema())){
						this.setTipo(ts.getTipo(t.getLexema()));
						return true;
					}else{
						throw new SemanticException("Error semantico en "+t.getLexema(),"La variable "+t.getLexema()+" no esta declarada");
					}
				}else{
					iTablaDeSimbolos tabla = SingleTabla.getInstance();
					try{
						if(tabla.checkParam(t.getLexema(),id1.getTipos())){
							this.setArbol(new NodoLlamada(t.getLexema(),id1.getArbolPasaje(),t));
							if(ts.declaredFunc(t.getLexema())){
								this.setTipo(ts.getTipoFuncProc(t.getLexema()));
								return true;
							}else{
								throw new SemanticException("Error semantico en "+t.getLexema(),"La Funcion o Proc "+t.getLexema()+" no esta declarada");
							}
						}else{
							throw new TypeException("Error pasando los parametros de la funcion "+t.getLexema(),"La cantidad o tipos de parametros es incorrecta");
						}
					}catch(TypeException te){
						throw te;
					}
				}
			}
		}catch(TableException e){
			throw new SemanticException("Error en la tabla de simbolos",e.getMessage());
		}
	}

	private boolean accionSemantica(Entero t) {
		this.setArbol(new HojaInteger(t));
		this.setTipo(Tipo.INTEGER);
		return true;
	}

	private boolean accionSemantica(Booleano t) {
		this.setArbol(new HojaBoolean(t));
		this.setTipo(Tipo.BOOLEAN);
		return true;
	}
}
