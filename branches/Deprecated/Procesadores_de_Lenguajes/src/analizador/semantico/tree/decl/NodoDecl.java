package analizador.semantico.tree.decl;

import java.util.ArrayList;

import analizador.semantico.tree.INodo;

public class NodoDecl implements INodo{
	
	private ArrayList<HojaDecl> lista;
	
	
	public NodoDecl(ArrayList<HojaDecl> lis){
		this.lista = lis;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODODECL;
	}

}
