package analizador.semantico.tree.exp;

import analizador.lexico.tokens.ID;
import analizador.semantico.tree.bloque.Sentencias;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoLlamada extends NodoAcceso implements Sentencias{
	
	private NodoPasaje parametros;
	
	public NodoLlamada(String id, NodoPasaje pasaje){
		super(id);
		this.parametros = pasaje;
		this.tipoNodo = TipoNodo.LLAMADA;
	}
	
	public NodoLlamada(String id, NodoPasaje pasaje,ID tkn){
		super(id,tkn);
		this.parametros = pasaje;
		this.tipoNodo = TipoNodo.LLAMADA;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.parametros.printTree();
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" ID: "+this.id;
	}
}
