package analizador.semantico.tree.func;

import analizador.semantico.tree.INodo;
import analizador.semantico.tree.bloque.NodoBloque1;
import analizador.semantico.tree.decl.NodoDecl;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoProc implements INodo{

	
	private String id;
	private NodoParam param;
	private NodoDecl decl;
	private NodoBloque1 bloque;
	protected TipoNodo tipoNodo;
	
	
	public NodoProc(String id, NodoParam param, NodoDecl decl, NodoBloque1 bloque){
		this.id = id;
		this.param =param;
		this.decl = decl;
		this.bloque = bloque;
		this.tipoNodo = TipoNodo.NODOPROC;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOPROC;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.param.printTree();
			this.decl.printTree();
			this.bloque.printTree();
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" ID: "+this.id;
	}
	
	public NodoParam getParam(){
		return this.param;
	}
	
	public NodoDecl getDecl(){
		return this.decl;
	}
	
	public NodoBloque1 getBloque(){
		return this.bloque;
	}
	
	public String getId(){
		return this.id;
	}
}
