package analizador.semantico.reglas.bloque;

import java.util.ArrayList;
import java.util.Stack;

import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.bloque.Mostrable;
import analizador.sintactico.estructuras.LRApilable;
import exceptions.AlgorithmicError;

public class MOSTRAR1 extends MOSTRAR implements ReglaSemantica {
	
	
	public MOSTRAR1(){
		super(MOSTRAR1.class.getSimpleName());
	}
	
	public MOSTRAR1(String s){
		super(s);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) {

		if (prod==null || prod.size()==0){
			this.lista = new ArrayList<Mostrable>();
		}else{
			if (prod.size()==2){
				try {
					return this.accionSemantica((MOSTRAR)prod.get(0));
				} catch (ClassCastException e) {
					throw new AlgorithmicError("Error algoritmico en la produccion MOSTRAR1","Produccion mal formada");
				}				
			}
			else{
				throw new AlgorithmicError("Error algoritmico en la produccion MOSTRAR1","Cantidad en produccion incorrecta");
			}
		}		
		return true;
	}
	

	private boolean accionSemantica(MOSTRAR mostrable) {
		this.lista=mostrable.lista;
		return true;
	}
	
}
