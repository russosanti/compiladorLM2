package analizador.semantico.tree.exp;


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
}
