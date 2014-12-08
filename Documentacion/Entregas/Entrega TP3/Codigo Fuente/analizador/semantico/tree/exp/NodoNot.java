package analizador.semantico.tree.exp;

import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;


public class NodoNot extends NodoExp{
	
	private TipoOperacion operacion = TipoOperacion.NOT;
	
	public NodoNot(NodoExp izq){
		super();
		this.setIzq(izq);
		this.tipoNodo = TipoNodo.NOT;
	}

	public TipoOperacion getTipoOperacion() {
		return this.operacion;
	}
	
	public NodoExp getNodo(){
		return this.getIzq();
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.getIzq().printTree();
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" OP: "+this.operacion;
	}
}
