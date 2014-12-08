package analizador.semantico.tree.bloque;

import analizador.semantico.tree.exp.NodoAccesoArray;
import analizador.semantico.tree.exp.NodoExp;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoAsigArray extends NodoAccesoArray implements Sentencias{
	
	private NodoExp asig;
	
	public NodoAsigArray(String id, NodoExp indice, NodoExp asig){
		super(id, indice);
		this.tipoNodo = TipoNodo.ASIGARRAY;
		this.asig = asig;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.ASIGARRAY;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.asig.printTree();
			this.getIndice().printTree();
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " + this.id + " Tipo Nodo: " +this.tipoNodo;
	}
}
