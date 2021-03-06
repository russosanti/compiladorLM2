package analizador.semantico.tree.func;

import java.util.ArrayList;
import java.util.Iterator;

import analizador.semantico.tree.INodo;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoParam implements INodo{
	private ArrayList<HojaParam> lista;
	
	public NodoParam(ArrayList<HojaParam> lis){
		this.lista = lis;
	}

	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOPARAM;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			Iterator<HojaParam> it = this.lista.iterator();
			prin.ident();
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
