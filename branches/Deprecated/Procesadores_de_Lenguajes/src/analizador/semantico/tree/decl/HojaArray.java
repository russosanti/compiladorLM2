package analizador.semantico.tree.decl;

import tabladesimbolos.Tipo;

public class HojaArray extends HojaVar{

	private int tamano;
	
	public HojaArray(String s, Tipo t,int tam) {
		super(s, t);
		this.tamano = tam;
	}

	public int getTamano() {
		return tamano;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.HOJAVAR;
	}
}
