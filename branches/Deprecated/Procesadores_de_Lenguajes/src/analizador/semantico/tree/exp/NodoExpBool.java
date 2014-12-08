package analizador.semantico.tree.exp;


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
}
