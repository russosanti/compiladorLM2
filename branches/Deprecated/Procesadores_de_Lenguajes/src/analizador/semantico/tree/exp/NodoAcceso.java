package analizador.semantico.tree.exp;

import analizador.lexico.tokens.ID;

public class NodoAcceso extends NodoExp{
	
	protected String id;
	private ID token;
	protected TipoNodo tipoNodo = TipoNodo.ACCESO;
	
	public NodoAcceso(String id){
		this. id = id;
	}
	
	public NodoAcceso(String id, ID tkn){
		this(id);
		this.token = tkn;
	}
}
