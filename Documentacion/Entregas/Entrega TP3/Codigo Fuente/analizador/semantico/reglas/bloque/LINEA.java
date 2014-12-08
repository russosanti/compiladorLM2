package analizador.semantico.reglas.bloque;

import java.util.Stack;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import tabladesimbolos.iTablaDeSimbolos;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;
import exceptions.TableException;
import exceptions.TypeException;
import analizador.lexico.tokens.ID;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.reglas.exp.EXP;
import analizador.semantico.reglas.func.FUNC;
import analizador.semantico.tree.bloque.NodoAsigAcceso;
import analizador.semantico.tree.bloque.NodoAsigArray;
import analizador.semantico.tree.bloque.NodoIf;
import analizador.semantico.tree.bloque.NodoWhile;
import analizador.semantico.tree.bloque.Sentencias;
import analizador.semantico.tree.exp.NodoLlamada;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;

public class LINEA extends NoTerminal implements ReglaSemantica{
	
	private Sentencias arbol;
	
	public LINEA() {
		super(LINEA.class.getSimpleName());
	}
	
	public LINEA(String n) {
		super(n);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod)throws SemanticException {
		if(prod==null || prod.size()==0){
			throw new AlgorithmicError("Error en el LINEA","La produccion no puede estar vacia");
		}else{
			try{
				if(prod.size()==1){
					//LINEA -> <FUNC>
					return this.accionSemantica((FUNC)prod.get(0));
				}else{
					if(prod.size()==2){
						//LINEA -> ID <ASIG>
						return this.accionSemantica((ID)prod.get(1),(ASIG)prod.get(0));
					}else{
						if(prod.size()==5){
							//LINEA -> WHILE <EXP> DO <BLOQUE2> END_WHILE
							Token t1 = (Token)prod.get(4);
							Token t2 = (Token)prod.get(2);
							Token t3 = (Token)prod.get(0);
							if(t1.getType()==TokenTypes.WHILE && t2.getType()==TokenTypes.DO && t3.getType()==TokenTypes.END_WHILE){
								return this.accionSemantica((EXP)prod.get(3),(BLOQUE2)prod.get(1));
							}else{
								throw new AlgorithmicError("Error algoritmico en la produccion LINEA", "Mal formada la sentencia While Do End-While");
							}
						}else{
							if(prod.size()==6){
								//LINEA -> IF <EXP> THEN <BLOQUE2> <BLOQUESI> END_IF
								Token t1 = (Token)prod.get(5);
								Token t2 = (Token)prod.get(3);
								Token t3 = (Token)prod.get(0);
								if(t1.getType()==TokenTypes.IF && t2.getType()==TokenTypes.THEN && t3.getType()==TokenTypes.END_IF){
									return this.accionSemantica((EXP)prod.get(4),(BLOQUE2)prod.get(2), (BLOQUESI)prod.get(1));
								}else{
									throw new AlgorithmicError("Error algoritmico en la produccion LINEA", "Mal Formada la sentencia If Then Else End-If");
								}
							}else{
								throw new AlgorithmicError("Error algoritmico en la produccion ASIG", "La cantiad es Incorrecta");
							}
						}
					}
				}
			}catch(ClassCastException e){
				throw new AlgorithmicError("Error algoritmico en la produccion ASIG", "La Produccion vino mal formada");
			}catch(IndexOutOfBoundsException ex){
				throw new AlgorithmicError("Error algoritmico en la produccion ASIG", ex.getMessage());
			}
		}
	}

	private boolean accionSemantica(FUNC func) {
		this.arbol = func.getArbol();
		return true;
	}

	private boolean accionSemantica(ID id, ASIG asig) throws SemanticException {
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		try{
			if(asig.getTipoNodo()==ASIG.TipoAsig.ACCESO){ //si entreo por acceso
				if(tabla.declaredID(id.getLexema())){
					Tipo tipoId = tabla.getTipo(id.getLexema());
					if(tipoId==asig.getTipo()){
						this.arbol = new NodoAsigAcceso(id.getLexema(),asig.getArbolExp());
						return true;
					}else{
						throw new TypeException("No se puede asignar un "+asig.getTipo()+" a una variable de tipo "+ tipoId);
					}
				}else{
					throw new SemanticException("La variable " + id.getLexema() + " no fue declarada");
				}
			}else{
				if(asig.getTipoNodo()==ASIG.TipoAsig.ARRAY){ //si entro por Array
					if(tabla.declaredID(id.getLexema())){
						Tipo tipoId = tabla.getTipo(id.getLexema());
						if(tipoId==asig.getTipo()){
							this.arbol = new NodoAsigArray(id.getLexema(),asig.getIndice(),asig.getArbolExp());
							return true;
						}else{
							throw new TypeException("No se puede asignar un "+asig.getTipo()+" a una variable de tipo "+ tipoId);
						}
					}else{
						throw new SemanticException("La variable " + id.getLexema() + " no fue declarada");
					}
				}else{
					if(asig.getTipoNodo()==ASIG.TipoAsig.LLAMADA){ //si entro por llamada
						if(tabla.declaredFunc(id.getLexema())){
							if(tabla.checkParam(id.getLexema(), asig.getTipoPasaje())){
								this.arbol = new NodoLlamada(id.getLexema(), asig.getArbolPasaje());
								return true;
							}else{
								throw new TypeException("Los tipos de los paramentros de " + id.getLexema() + " no coinciden con los tipos declarados");
							}
						}else{
							throw new SemanticException("El procedimiento o funcion " + id.getLexema() + " no fue declarado");
						}
					}else{
						throw new AlgorithmicError("Error en LINEA","Error al elegir el tipo de nodo de ASIG");
					}
				}
			}
		}catch(TableException te){
			throw new AlgorithmicError("Error al buscar en la tabla de simbolos",te.getMessage());
		}
	}

	private boolean accionSemantica(EXP exp, BLOQUE2 bloque2) throws TypeException {
		if(exp.getTipo()==Tipo.BOOLEAN){
			this.arbol = new NodoWhile(exp.getArbol(), bloque2.lista);
			return true;
		}else{
			throw new TypeException("El tipo de la expresion dentro de la condicion del while debe ser booleana");
		}
	}

	private boolean accionSemantica(EXP exp, BLOQUE2 bloque2, BLOQUESI bloquesi) throws TypeException {
		if(exp.getTipo()==Tipo.BOOLEAN){
			this.arbol = new NodoIf(exp.getArbol(), bloque2.lista, bloquesi.lista);
			return true;
		}else{
			throw new TypeException("El tipo de la expresion dentro de la condicion del if debe ser booleana");
		}
	}

	public Sentencias getArbol() {
		return arbol;
	}
	
}
