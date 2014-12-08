package analizador.semantico.tree.bloque;

import java.util.ArrayList;
import java.util.Iterator;
import analizador.semantico.tree.INodo;
import analizador.semantico.tree.exp.NodoExp;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoWhile implements INodo, Sentencias{
	protected NodoExp condicion;
	protected ArrayList<Sentencias> bloque;
	
	
	public NodoWhile(NodoExp condicion, ArrayList<Sentencias> bloque){
		this.condicion = condicion;
		this.bloque =bloque;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOWHILE;
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
			prin.deident();
		}catch(Exception e){
			
		}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo();
	}
}
