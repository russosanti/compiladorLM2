package analizador.semantico.tree.exp;

import java.util.ArrayList;
import java.util.Iterator;

import tabladesimbolos.Tipo;
import analizador.semantico.tree.Nodo;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoPasaje extends Nodo<NodoExp>{

	protected TipoNodo tipoNodo = TipoNodo.PASAJE;
	private ArrayList<Tipo> tipo;
	
	@Override
	public TipoNodo getTipoNodo() {
		return this.tipoNodo;
	}

	public ArrayList<Tipo> getTipos() {
		return tipo;
	}

	public void setTipos(ArrayList<Tipo> tipo) {
		this.tipo = tipo;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			Iterator<NodoExp> it = this.hijos.iterator();
			prin.ident();
			while(it.hasNext()){
				it.next().printTree();
			}
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " + this.id + " Tipo Nodo: " +this.tipoNodo;
	}

}
