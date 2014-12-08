package analizador.semantico.tree.exp;

import analizador.lexico.tokens.ID;

public class NodoAccesoArray extends NodoAcceso{
	
	private NodoExp indice;
	protected TipoNodo tipoNodo = TipoNodo.ACCESOARRAY;
	
	/** Constructor para el nodo
	 * @param id - Id de la variable
	 * @param indice - Posicion en el vector */
	public NodoAccesoArray(String id, NodoExp indice){
		super(id);
		this.indice = indice;
	}
	
	/** Constructor para le NodoAccessoArray
	 * @param id - Id de la variable
	 * @param indice - Posicion en el vector
	 * @param tkn - Tkn del ID */
	public NodoAccesoArray(String id, NodoExp indice, ID tkn){
		super(id,tkn);
		this.indice = indice;
	}
}
