package analizador.semantico.tree.func;

import java.util.ArrayList;

import analizador.semantico.tree.INodo;

public class NodoParam implements INodo{
	private ArrayList<HojaParam> lista;
	
	public NodoParam(ArrayList<HojaParam> lis){
		this.lista = lis;
	}

	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOPARAM;
	}
}
