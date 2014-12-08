package analizador.semantico.tree.bloque;

import analizador.semantico.tree.exp.NodoAcceso;
import analizador.semantico.tree.exp.NodoExp;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

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
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.exp.printTree();
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " + this.id + " Tipo Nodo: " +this.tipoNodo;
	}
}
