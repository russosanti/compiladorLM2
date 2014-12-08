package analizador.semantico.tree.decl;

import tabladesimbolos.Tipo;
import analizador.semantico.tree.INodo;

public abstract class HojaDecl implements INodo{
	
	private String id;
	private Tipo tipo;
	
	
	protected HojaDecl(String id, Tipo t) {
		this.id = id;
		this.tipo = t;
	}
	
	
	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}

	public Tipo getTipo() {
		return tipo;
	}

	protected void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
}
