package analizador.semantico.reglas.bloque;

import java.util.ArrayList;
import java.util.Stack;
import exceptions.AlgorithmicError;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.bloque.Sentencias;
import analizador.sintactico.estructuras.LRApilable;

public class BLOQUESI extends BLOQUE2 implements ReglaSemantica{
	
	public BLOQUESI() {
		super(BLOQUESI.class.getSimpleName());
	}
	
	public BLOQUESI(String n) {
		super(n);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod){
		if(prod == null ||  prod.size() == 0){
			return this.accionSemantica();
		}else{
			if(prod.size()==2){
				try{
					return this.accionSemantica((BLOQUE2)prod.get(0));
				}catch(ClassCastException e){
					throw new AlgorithmicError("Error algoritmico en la produccion BLOQUESI", "La Produccion vino mal formada");
				}catch(IndexOutOfBoundsException ex){
					throw new AlgorithmicError("Error algoritmico en la produccion BLOQUESI", ex.getMessage());
				}
			}else{
				throw new AlgorithmicError("Error en BLOQUESI","Tamaño incorrecto de producciones");
			}
		}
	}

	private boolean accionSemantica(BLOQUE2 bloque2) {
		this.lista = bloque2.getLista();
		return true;
	}

	private boolean accionSemantica() {
		this.lista = new ArrayList<Sentencias>();
		return true;
	}
	
}
