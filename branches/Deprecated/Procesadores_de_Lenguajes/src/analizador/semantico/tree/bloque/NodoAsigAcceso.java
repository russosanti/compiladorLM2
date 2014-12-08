package analizador.semantico.tree.bloque;

import analizador.semantico.tree.exp.NodoAcceso;
import analizador.semantico.tree.exp.NodoExp;

public class NodoAsigAcceso extends NodoAcceso implements Sentencias{
	
	private NodoExp exp;
	
	public NodoAsigAcceso(String id, NodoExp exp){
		super(id);
		this.exp = exp;
		this.tipoNodo = TipoNodo.ASIGACCESSO;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.ASIGACCESSO;
	}
}
