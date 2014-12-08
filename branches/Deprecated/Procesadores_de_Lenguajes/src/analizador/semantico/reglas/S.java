package analizador.semantico.reglas;

import java.util.Stack;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.iTablaDeSimbolos;
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
		if(!tabla.hasMain()){
			tabla.setErrorMode();
			throw new SemanticException("Error en el programa!! Falta el procedure main();");
		}
		return true;
	}

	public NodoS getArbol() {
		return arbol;
	}

}