package analizador.semantico.tree.func;

import tabladesimbolos.Tipo;
import analizador.semantico.tree.bloque.NodoBloque1;
import analizador.semantico.tree.decl.NodoDecl;
import analizador.semantico.tree.exp.NodoExp;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoFunc extends NodoProc{

	private Tipo tipoRetorno;
	private NodoExp retorno;
	
	public NodoFunc(String id, NodoParam param, NodoDecl decl,NodoBloque1 bloque, Tipo tipo,NodoExp retorno) {
		super(id, param, decl, bloque);
		this.tipoRetorno = tipo;
		this.retorno = retorno;
		this.tipoNodo = TipoNodo.NODOFUNC;
	}

	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOFUNC;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.getParam().printTree();
			this.getDecl().printTree();
			this.getBloque().printTree();
			this.retorno.printTree();
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" ID: "+this.getId() + "Tipo Retorno: " + this.tipoRetorno;
	}
}
