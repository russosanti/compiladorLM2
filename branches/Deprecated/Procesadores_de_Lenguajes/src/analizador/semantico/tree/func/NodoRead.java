package analizador.semantico.tree.func;

import analizador.semantico.tree.INodo;
import analizador.semantico.tree.bloque.Sentencias;
import analizador.semantico.tree.exp.NodoExp;

public class NodoRead implements INodo,iFuncList,Sentencias{
	
	private String id;
	private NodoExp indice;
	private boolean isArray;
	
	
	public NodoRead(String id){
		this.id = id;
		this.isArray = false;
	}
	
	public NodoRead(String id, NodoExp indice){
		this.id = id;
		this.indice = indice;
		this.isArray = true;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOREAD;
	}

	@Override
	public boolean isInput() {
		return true;
	}
	
}
