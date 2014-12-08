package analizador.semantico.tree.func;

import analizador.semantico.tree.INodo;
import analizador.semantico.tree.bloque.NodoBloque1;
import analizador.semantico.tree.decl.NodoDecl;

public class NodoProc implements INodo{

	
	private String id;
	private NodoParam param;
	private NodoDecl decl;
	private NodoBloque1 bloque;
	
	
	public NodoProc(String id, NodoParam param, NodoDecl decl, NodoBloque1 bloque){
		this.id = id;
		this.param =param;
		this.decl = decl;
		this.bloque = bloque;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOPROC;
	}

}
