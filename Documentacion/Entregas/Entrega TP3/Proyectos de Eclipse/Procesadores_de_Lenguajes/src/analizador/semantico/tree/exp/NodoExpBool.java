package analizador.semantico.tree.exp;

import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;


public class NodoExpBool extends NodoExp{
	
	private TipoOperacion operacion;
	
	public NodoExpBool(NodoExp izq, TipoOperacion op, NodoExp der){
		super(izq,der);
		this.operacion = op;
		this.tipoNodo = TipoNodo.NODOEXPBOOL;
	}
	
	public enum TipoOperacion{
		
		IGUALDAD("="),
		MAYOR_MENOR(">");
		
		private String desc;
		
		private TipoOperacion(String s){
			this.desc = s;
		}
		
		public String descripcion(){
			return this.desc;
		}
	}

	public TipoOperacion getTipoOperacion() {
		return this.operacion;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.getIzq().printTree();
			this.getDer().printTree();
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" OP: "+this.operacion;
	}
}
