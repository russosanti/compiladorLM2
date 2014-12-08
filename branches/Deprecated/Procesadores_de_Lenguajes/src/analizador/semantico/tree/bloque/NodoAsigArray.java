package analizador.semantico.tree.bloque;

import analizador.semantico.tree.exp.NodoAccesoArray;
import analizador.semantico.tree.exp.NodoExp;

public class NodoAsigArray extends NodoAccesoArray implements Sentencias{
	
	private NodoExp asig;
	
	public NodoAsigArray(String id, NodoExp indice, NodoExp asig){
		super(id, indice);
		this.tipoNodo = TipoNodo.ASIGARRAY;
		this.asig = asig;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.ASIGARRAY;
	}
}
