package analizador.semantico.tree.exp;

import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;
import exceptions.AlgorithmicError;


public class NodoExpBool extends NodoExp{
	
	private TipoOperacion operacion;
	
	public NodoExpBool(NodoExp izq, TipoOperacion op, NodoExp der){
		super(izq,der);
		this.operacion = op;
		this.tipoNodo = TipoNodo.NODOEXPBOOL;
	}
	
	public enum TipoOperacion{
		
		IGUALDAD("="),
		NOIGUALDAD("<>"),
		MAYOR(">"),
		MENOR("<"),
		MAYORIGUAL(">="),
		MENORIGUAL("<=");
		
		private String desc;
		
		private TipoOperacion(String s){
			this.desc = s;
		}
		
		public String descripcion(){
			return this.desc;
		}
		
		public static TipoOperacion tipoValueOf(String s) throws AlgorithmicError{
			switch (s){
				case "=":
					return IGUALDAD;
				case "<>":
					return NOIGUALDAD;
				case ">":
					return MAYOR;
				case "<":
					return MENOR;
				case ">=":
					return MAYORIGUAL;
				case "<=":
					return MENORIGUAL;
			}
			throw new AlgorithmicError("Error parseando operacion booleana");
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
