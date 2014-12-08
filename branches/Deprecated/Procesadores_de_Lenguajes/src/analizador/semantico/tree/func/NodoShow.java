package analizador.semantico.tree.func;

import java.util.ArrayList;

import analizador.semantico.tree.INodo;
import analizador.semantico.tree.bloque.Mostrable;
import analizador.semantico.tree.bloque.Sentencias;

public class NodoShow implements INodo,iFuncList,Sentencias{

	private ArrayList<Mostrable> lista;
	public boolean isShowLN;
	public String funcName;
	
	public NodoShow(String str,ArrayList<Mostrable> l){
		this.lista = l;
		this.funcName = str;
		if(str.equalsIgnoreCase("show")){
			this.isShowLN = false;
		}else
			this.isShowLN = true;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOSHOW;
	}

	@Override
	public boolean isInput() {
		return false;
	}
}
