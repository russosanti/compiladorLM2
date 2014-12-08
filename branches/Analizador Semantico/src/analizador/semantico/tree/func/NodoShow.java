package analizador.semantico.tree.func;

import java.util.ArrayList;
import java.util.Iterator;

import analizador.semantico.tree.INodo;
import analizador.semantico.tree.bloque.Mostrable;
import analizador.semantico.tree.bloque.Sentencias;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoShow implements INodo,iFuncList,Sentencias{

	private ArrayList<Mostrable> lista;
	public boolean isShowLN;
	public String funcName;
	
	public NodoShow(String str,ArrayList<Mostrable> l){
		this.lista = l;
		this.funcName = str;
		if(str.equalsIgnoreCase("show")){
			this.isShowLN = false;
		}else
			this.isShowLN = true;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOSHOW;
	}

	@Override
	public boolean isInput() {
		return false;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			Iterator<Mostrable> it = this.lista.iterator();
			while(it.hasNext()){
				it.next().printTree();
			}
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" ID: "+this.funcName + "Es ShowLN: " + this.isShowLN;
	}
}
