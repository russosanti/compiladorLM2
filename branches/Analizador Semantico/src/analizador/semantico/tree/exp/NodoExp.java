package analizador.semantico.tree.exp;

import tabladesimbolos.Tipo;
import analizador.semantico.tree.INodo;
import analizador.semantico.tree.bloque.Mostrable;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoExp implements INodo, Mostrable{
	
	private TipoOperacion operacion;
	private NodoExp izq;
	private NodoExp der;
	protected TipoNodo tipoNodo;
	private Tipo tipo;
	
	protected NodoExp(){
		this.tipoNodo = TipoNodo.EXP;
	}
	
	protected NodoExp(NodoExp izq, NodoExp der){
		this.setIzq(izq);
		this.der = der;
		this.tipoNodo = TipoNodo.EXP;
	}
	
	protected NodoExp(NodoExp izq, NodoExp der, Tipo tipo){
		this.setIzq(izq);
		this.der = der;
		this.tipo = tipo;
		this.tipoNodo = TipoNodo.EXP;
	}
	
	public NodoExp(NodoExp izq, TipoOperacion op, NodoExp der){
		this.setIzq(izq);
		this.operacion = op;
		this.der = der;
		this.tipoNodo = TipoNodo.EXP;
	}
	
	public NodoExp(NodoExp izq, TipoOperacion op, NodoExp der, Tipo tipo){
		this.setIzq(izq);
		this.operacion = op;
		this.der = der;
		this.tipoNodo = TipoNodo.EXP;
		this.tipo = tipo;
	}
	
	public enum TipoOperacion{
		
		AND("AND"),
		OR("OR"),
		NOT("NOT"),
		SUM("SUM"),
		REST("REST"),
		DIV("DIV"),
		PROD("PROD");
		
		private String desc;
		
		private TipoOperacion(String s){
			this.desc = s;
		}
		
		public String descripcion(){
			return this.desc;
		}
	}

	@Override
	public TipoNodo getTipoNodo() {
		return this.tipoNodo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public NodoExp getIzq() {
		return izq;
	}
	
	public NodoExp getDer() {
		return der;
	}

	protected void setIzq(NodoExp izq) {
		this.izq = izq;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.izq.printTree();
			this.der.printTree();
			prin.deident();
		}catch(Exception e){
			
		}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" OP: "+this.operacion;
	}
}
