package analizador.semantico.tree.decl;

import exceptions.CodeException;
import tabladesimbolos.Tipo;
import analizador.semantico.tree.INodo;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public abstract class HojaDecl implements INodo{
	
	private String id;
	private Tipo tipo;
	
	
	protected HojaDecl(String id, Tipo t) {
		this.id = id;
		this.tipo = t;
	}
	
	
	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}

	public Tipo getTipo() {
		return tipo;
	}

	protected void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" Tipo: "+this.getTipo();
	}
	
	public abstract void generateCode() throws CodeException;
	
}
