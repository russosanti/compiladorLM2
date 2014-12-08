package analizador.semantico.tree.bloque;

import java.util.ArrayList;

import analizador.semantico.tree.exp.NodoExp;

public class NodoIf extends NodoWhile{
	
	protected ArrayList<Sentencias> bloqueSi;
	
	public NodoIf(NodoExp condicion, ArrayList<Sentencias> bloque, ArrayList<Sentencias> bloqueSi){
		super(condicion,bloque);
		this.bloqueSi = bloqueSi;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOIF;
	}
}
