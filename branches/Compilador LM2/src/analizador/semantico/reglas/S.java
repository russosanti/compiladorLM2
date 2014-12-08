package analizador.semantico.reglas;

import java.util.Iterator;
import java.util.Stack;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.iTablaDeSimbolos;
import tabladesimbolos.declaraciones.DeclFuncProcDesconocidas;
import analizador.semantico.reglas.bloque.BLOQUE;
import analizador.semantico.reglas.bloque.GLOBALES;
import analizador.semantico.tree.NodoS;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;

public class S extends NoTerminal implements ReglaSemantica{

	private NodoS arbol; //Usa el Nodo Generico ya que la generacion de codigo no hace nada
	
	public S() {
		super(S.class.getSimpleName());
	}
	
	public S(String s) {
		super(s);
	}
	
	public boolean accionSemantica(Stack<LRApilable> argumentos) throws SemanticException{
		if(argumentos.size() == 2){
			try{
				return this.accionSemantica((GLOBALES)argumentos.elementAt(1),(BLOQUE)argumentos.elementAt(0));
			}catch(ClassCastException e){
				throw new AlgorithmicError("Error algoritmico en la produccion S", "La Produccion vino mal formada");
			}catch(IndexOutOfBoundsException ex){
				throw new AlgorithmicError("Error algoritmico en la produccion S", ex.getMessage());
			}
		}else{
			throw new AlgorithmicError("Error algoritmico en la produccion ASIG", "La cantiad es Incorrecta");
		}
	}
	
	private boolean accionSemantica(GLOBALES glob, BLOQUE b) throws SemanticException{
		this.arbol = new NodoS(glob.getArbol(),b.getArbol());
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		
		if(checkNonDeclared()){
			if(!tabla.hasMain()){
				tabla.setErrorMode();
				throw new SemanticException("Error en el programa!! Falta el procedure main();");
			}
			return true;
		}else{
			return false;
		}
		
	}

	public NodoS getArbol() {
		return arbol;
	}
	
	private boolean checkNonDeclared() throws SemanticException{
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		Iterator<DeclFuncProcDesconocidas> it = tabla.getIteratorDesconocidas();
		StringBuilder sb = new StringBuilder();
		
		DeclFuncProcDesconocidas decl;
		while(it.hasNext()){
			decl = it.next();
			if(tabla.declaredFunc(decl.getID())){
				try{
					if(!tabla.checkParam(decl.getID(), decl.getTipoParams())){
						sb.append("Error semantico en Fila: "+ decl.getToken().getFila() + 
								" Columna: "+ decl.getToken().getColumna() +". ");
						sb.append("Pasaje de Parametros Incorrecto");
					}
				}catch(SemanticException e){
					sb.append(e.getTitle());
					sb.append(e.getMessage());
				}
			}else{
				sb.append("Error semantico en Fila: "+ decl.getToken().getFila() + 
						" Columna: "+ decl.getToken().getColumna() +". ");
				sb.append("La Funcion o Proc "+decl.getID()+" no esta declarada");
			}
		}
		String error = new String(sb);
		if(error.length()==0){
			return true;
		}else{
			throw new SemanticException("Error con funciones recursivas",error);
		}
	}
}