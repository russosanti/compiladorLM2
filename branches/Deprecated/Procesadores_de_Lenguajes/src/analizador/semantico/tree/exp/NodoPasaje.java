package analizador.semantico.tree.exp;

import java.util.ArrayList;

import tabladesimbolos.Tipo;
import analizador.semantico.tree.Nodo;

public class NodoPasaje extends Nodo<NodoExp>{

	protected TipoNodo tipoNodo = TipoNodo.PASAJE;
	private ArrayList<Tipo> tipo;
	
	@Override
	public TipoNodo getTipoNodo() {
		return this.tipoNodo;
	}

	public ArrayList<Tipo> getTipos() {
		return tipo;
	}

	public void setTipos(ArrayList<Tipo> tipo) {
		this.tipo = tipo;
	}
	
	

}
