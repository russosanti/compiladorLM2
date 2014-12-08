package analizador.semantico.tree.func;

import exceptions.CodeException;
import generadorCodigo.ParameterRetainer;
import generadorCodigo.TemporalManager;
import tabladesimbolos.SingleTabla;
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
	
	@Override
	public void generateCode() throws CodeException {
		ITree cg = ExecutionTree.getInstance();
		cg.insertCode(";Inicio Procedimiento "+this.getId());
		cg.insertCode("PROC "+this.getId());
		cg.codeIdent();
		SingleTabla.getInstance().setContexto(this.getId());
		if(getParam().listSize()>0){
			cg.insertCode("push bp");
			cg.insertCode("mov bp, sp");
			cg.insertCode("");
		}
		this.getParam().generateCode();
		//Siempre tengo la lista de los parametros dentro del codigo de cada procedimiento
		ParameterRetainer pr = ParameterRetainer.getInstance(this.getId());
		pr.setLista(this.getParam().getLista());
		this.getDecl().generateCode();
		this.getBloque().generateCode();
		
		//Le paso los parametros al PROC o FUNC
		if(getParam().listSize()>0){
			cg.insertCode("pop bp");
			cg.codeDeident();
			cg.insertCode("RET "+2*getParam().listSize());
		}else{
			cg.codeDeident();
			cg.insertCode("RET"); //Todas las expresiones se terminan guardando en AX
		}
		cg.insertCode("ENDP");
		cg.insertCode(";Fin Procedimiento "+this.getId());
		cg.insertCode("");
		TemporalManager tm = TemporalManager.getInstance();
		tm.resetTemporales();
	}
}
