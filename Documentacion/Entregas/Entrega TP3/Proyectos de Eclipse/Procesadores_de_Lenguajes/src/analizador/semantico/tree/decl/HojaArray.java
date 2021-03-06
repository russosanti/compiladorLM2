package analizador.semantico.tree.decl;

import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;
import tabladesimbolos.Tipo;

public class HojaArray extends HojaVar{

	private int tamano;
	
	public HojaArray(String s, Tipo t,int tam) {
		super(s, t);
		this.tamano = tam;
	}

	public int getTamano() {
		return tamano;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.HOJAVAR;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" ID: "+this.getId()+" Tipo: "+this.getTipo()+" Tamano: "+this.getTamano();
	}
	
}
