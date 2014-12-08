package analizador.semantico.tree.bloque;

import java.util.ArrayList;
import java.util.Iterator;

import analizador.semantico.tree.exp.NodoExp;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoIf extends NodoWhile{
	
	protected ArrayList<Sentencias> bloqueSi;
	
	public NodoIf(NodoExp condicion, ArrayList<Sentencias> bloque, ArrayList<Sentencias> bloqueSi){
		super(condicion,bloque);
		this.bloqueSi = bloqueSi;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOIF;
	}
	
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.condicion.printTree();
			Iterator<Sentencias> it = this.bloque.iterator();
			while(it.hasNext()){
				it.next().printTree();
			}
			it = this.bloqueSi.iterator();
			while(it.hasNext()){
				it.next().printTree();
			}
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo();
	}
}
