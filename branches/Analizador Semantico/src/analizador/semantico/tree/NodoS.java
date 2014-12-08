package analizador.semantico.tree;

import analizador.semantico.tree.bloque.NodoBloque;
import analizador.semantico.tree.bloque.NodoGlobales;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoS implements INodo{
	
	private NodoGlobales glob;
	private NodoBloque bloque;
	
	public NodoS(NodoGlobales g, NodoBloque b){
		this.glob = g;
		this.bloque =b;
	}

	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.S;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.glob.printTree();
			this.bloque.printTree();
			prin.deident();
		}catch(Exception e){
			
		}
	}
	
	public String toString(){
		return "Nombre: " + this.getTipoNodo();
	}
}
