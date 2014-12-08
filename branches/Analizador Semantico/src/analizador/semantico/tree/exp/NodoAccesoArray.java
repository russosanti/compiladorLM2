package analizador.semantico.tree.exp;

import analizador.lexico.tokens.ID;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

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

	public NodoExp getIndice() {
		return indice;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.indice.printTree();
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" ID: "+this.id;
	}
}
