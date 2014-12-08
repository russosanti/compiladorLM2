package analizador.semantico.tree.func;

import tabladesimbolos.Tipo;
import analizador.semantico.tree.INodo;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class HojaParam implements INodo{
	
	private boolean isRef;
	private String id;
	private Tipo tipo;
	
	public HojaParam(boolean isRef, String id, Tipo tipo){
		this.isRef = isRef;
		this.id = id;
		this.tipo = tipo;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.HOJAPARAM;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public String getId() {
		return id;
	}

	public boolean isRef() {
		return isRef;
	}

	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Tipo Nodo: " +this.getTipoNodo()+" Nombre: "+this.id +" Tipo: "+this.getTipo()+" isRef: "+this.isRef;
	}
	
	@Override
	public void generateCode() {
		//no hace nada ya que lo trato en NodoFunc
	}
}
