package analizador.semantico.reglas.bloque;

import java.util.Stack;

import exceptions.AlgorithmicError;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.reglas.decl.DECGL;
import analizador.semantico.tree.bloque.NodoGlobales;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;

public class GLOBALES extends NoTerminal implements ReglaSemantica{
	
	private NodoGlobales arbol;
	
	public GLOBALES(){
		super(GLOBALES.class.getSimpleName());
	}
	
	public GLOBALES(String s){
		super(s);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) {
		if(prod==null || prod.size()==0){
			return this.accionSemantica();
		}else{
			try{
				if(prod.size()==1){
					return this.accionSemantica((DECGL)prod.get(0));
				}else{
					throw new AlgorithmicError("Error algoritmico en la produccion FP", "La cantidad esta mal");
				}
			}catch(ClassCastException e){
				throw new AlgorithmicError("Error algoritmico en la produccion FP", "La Produccion vino mal formada");
			}catch(IndexOutOfBoundsException ex){
				throw new AlgorithmicError("Error algoritmico en la produccion FP", ex.getMessage());
			}
		}
	}

	private boolean accionSemantica() {
		this.arbol = new NodoGlobales();
		return true;
	}

	private boolean accionSemantica(DECGL decgl) {
		this.arbol = new NodoGlobales(decgl.getLista());
		return true;
	}

	public NodoGlobales getArbol() {
		return arbol;
	}
}
