package analizador.semantico.tree.bloque;

import java.util.ArrayList;

import analizador.semantico.tree.INodo;
import analizador.semantico.tree.exp.NodoExp;

public class NodoWhile implements INodo, Sentencias{
	protected NodoExp condicion;
	protected ArrayList<Sentencias> bloque;
	
	
	public NodoWhile(NodoExp condicion, ArrayList<Sentencias> bloque){
		this.condicion = condicion;
		this.bloque =bloque;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOWHILE;
	}
}
