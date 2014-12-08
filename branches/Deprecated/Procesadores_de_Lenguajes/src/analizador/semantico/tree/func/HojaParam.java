package analizador.semantico.tree.func;

import tabladesimbolos.Tipo;
import analizador.semantico.tree.INodo;

public class HojaParam implements INodo{
	
	private boolean isRef;
	private String id;
	private Tipo tipo;
	
	public HojaParam(boolean isRef, String id, Tipo tipo){
		this.isRef = isRef;
		this.id = id;
		this.tipo = tipo;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.HOJAPARAM;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public String getId() {
		return id;
	}

	public boolean isRef() {
		return isRef;
	}

}
