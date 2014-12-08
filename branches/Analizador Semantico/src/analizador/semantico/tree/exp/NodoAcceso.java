package analizador.semantico.tree.exp;

import analizador.lexico.tokens.ID;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoAcceso extends NodoExp{
	
	protected String id;
	private ID token;
	protected TipoNodo tipoNodo = TipoNodo.ACCESO;
	
	public NodoAcceso(String id){
		this. id = id;
	}
	
	public NodoAcceso(String id, ID tkn){
		this(id);
		this.token = tkn;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" ID: "+this.id;
	}
}
