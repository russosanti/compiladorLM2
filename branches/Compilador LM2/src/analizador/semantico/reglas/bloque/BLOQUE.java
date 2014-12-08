package analizador.semantico.reglas.bloque;

import java.util.Stack;
import exceptions.AlgorithmicError;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.reglas.func.FP;
import analizador.semantico.tree.bloque.NodoBloque;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;

public class BLOQUE extends NoTerminal implements ReglaSemantica {
	
	private NodoBloque arbol;
	
	public BLOQUE(){
		super(BLOQUE.class.getSimpleName());
	}
	
	public BLOQUE(String s){
		super(s);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) {
		if(prod==null || prod.size()==0){
			return this.accionSemantica();
		}else{
			try{
				if(prod.size()==2){
					return this.accionSemantica((FP)prod.get(1),(BLOQUE)prod.get(0));
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
		this.arbol = new NodoBloque();
		return true;
	}

	private boolean accionSemantica(FP fp, BLOQUE bloque) {
		this.arbol = bloque.getArbol();
		this.arbol.addFirst(fp.getNodo());
		return true;
	}
	
	public NodoBloque getArbol(){
		return this.arbol;
	}
}
