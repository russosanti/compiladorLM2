package analizador.semantico.reglas;

import java.util.Stack;
import exceptions.AlgorithmicError;
import analizador.semantico.tree.NodoS;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;

public class SPrima extends NoTerminal implements ReglaSemantica{

	private NodoS arbol; //Usa el Nodo Generico ya que la generacion de codigo no hace nada
	
	
	public SPrima() {
		super(SPrima.class.getSimpleName());
	}
	
	public SPrima(String s) {
		super(s);
	}
	
	public boolean accionSemantica(Stack<LRApilable> argumentos){
		if(argumentos.size() == 1){
			try{
				return this.accionSemantica((S)argumentos.elementAt(0));
			}catch(ClassCastException e){
				throw new AlgorithmicError("Error algoritmico en la produccion S", "La Produccion vino mal formada");
			}catch(IndexOutOfBoundsException ex){
				throw new AlgorithmicError("Error algoritmico en la produccion S", ex.getMessage());
			}
		}else{
			throw new AlgorithmicError("Error algoritmico en la produccion ASIG", "La cantiad es Incorrecta");
		}
	}
	
	private boolean accionSemantica(S s){
		this.arbol = s.getArbol();
		return true;
	}
}