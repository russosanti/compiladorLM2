package analizador.semantico.tree.func;

import exceptions.CodeException;
import generadorCodigo.ParameterRetainer;
import generadorCodigo.TemporalManager;
import tabladesimbolos.SingleTabla;
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
	
	@Override
	public void generateCode() throws CodeException {
		ITree cg = ExecutionTree.getInstance();
		cg.insertCode(";Inicio Funcion "+this.getId());
		cg.insertCode("PROC "+this.getId());
		cg.codeIdent();
		SingleTabla.getInstance().setContexto(this.getId());
		if(getParam().listSize()>0){
			cg.insertCode("push bp");
			cg.insertCode("mov bp, sp");
			cg.insertCode("");
		}
		this.getParam().generateCode();
		//Siempre tengo la lista de los parametros dentro del codigo de cada funcion
		ParameterRetainer pr = ParameterRetainer.getInstance(this.getId());
		pr.setLista(this.getParam().getLista());
		this.getBloque().generateCode();
		
		//Aca le paso los parametros
		this.retorno.generateCode();
		if(getParam().listSize()>0){
			cg.insertCode("pop bp");
			cg.codeDeident();
			cg.insertCode("RET "+2*getParam().listSize());
		}else{
			cg.codeDeident();
			cg.insertCode("RET"); //Todas las expresiones se terminan guardando en AX
		}
		cg.insertCode("ENDP");
		cg.insertCode(";Fin Funcion "+this.getId());
		cg.insertCode("");
		TemporalManager tm = TemporalManager.getInstance();
		tm.resetTemporales();
	}
}
