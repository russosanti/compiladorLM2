package analizador.semantico.tree.exp;

import exceptions.CodeException;
import generadorCodigo.TemporalManager;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;


public class NodoNot extends NodoExp{
	
	private TipoOperacion operacion = TipoOperacion.NOT;
	
	public NodoNot(NodoExp izq){
		super();
		this.setIzq(izq);
		this.tipoNodo = TipoNodo.NOT;
	}
	
	@Override
	public TipoOperacion getOperacion() {
		return this.operacion;
	}
	
	public NodoExp getNodo(){
		return this.getIzq();
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.getIzq().printTree();
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" OP: "+this.operacion;
	}
	
	@Override
	public void generateCode() throws CodeException {
		try{
			@SuppressWarnings("unused")
			Hoja h = (Hoja)this.getIzq();
			this.getIzq().generateCode();
		}catch(ClassCastException cce){
			//No hago nada xq ya esta en AX solo llamo al generar codigo
			this.getIzq().generateCode();
		}
		ITree cg = ExecutionTree.getInstance();
		cg.insertCode("NOT AX");
		if(this.needsTemporal()){
			TemporalManager tm = TemporalManager.getInstance();
			String temp = tm.getTemporal();
			this.setTemporalUsed(temp);
			cg.insertCode("MOV " + temp +",AX");
		}
	}
}
