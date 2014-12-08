package analizador.semantico.tree.bloque;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.iTablaDeSimbolos;
import tabladesimbolos.declaraciones.DeclArray;
import exceptions.CodeAlgorithmicError;
import exceptions.CodeException;
import exceptions.TableException;
import generadorCodigo.TemporalManager;
import analizador.semantico.tree.exp.NodoAccesoArray;
import analizador.semantico.tree.exp.NodoExp;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoAsigArray extends NodoAccesoArray implements Sentencias{
	
	private NodoExp asig;
	
	public NodoAsigArray(String id, NodoExp indice, NodoExp asig){
		super(id, indice);
		this.tipoNodo = TipoNodo.ASIGARRAY;
		this.asig = asig;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.ASIGARRAY;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.asig.printTree();
			this.getIndice().printTree();
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " + this.id + " Tipo Nodo: " +this.tipoNodo;
	}
	
	@Override
	public void generateCode() throws CodeException {
		ITree cg = ExecutionTree.getInstance();
		this.asig.setNeedsTemporal();
		this.asig.generateCode();
		String array = this.getValor();
		cg.insertCode("MOV AX, "+this.asig.getTemporal());
		cg.insertCode("MOV "+array+", AX");
		TemporalManager tm = TemporalManager.getInstance();
		tm.useTemp();
	}
	
	@Override
	/** Hay que mover el indice a BX */
	public String getValor() throws CodeException{
		ITree cg = ExecutionTree.getInstance();
		this.getIndice().generateCode();		
		cg.insertCode("MOV BX, AX");
		
		//Checkeo que el indice que accede al array este dentro del limite
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		DeclArray decl;
		try {
			decl = (DeclArray)tabla.get(this.id);
			int tamano = decl.getTamano();
			cg.insertCode("CMP BX,"+tamano);			
			cg.insertCode("JL Less"+System.identityHashCode(this)+";");
			cg.insertCode("PRINTN \"Index out of bounds\" ");
			cg.insertCode("MOV AH, 4Ch");
			cg.insertCode("INT 21h");
			cg.insertCode("Less"+System.identityHashCode(this)+":");
			
			cg.insertCode("CMP BX, -1");			
			cg.insertCode("JG Greater"+System.identityHashCode(this)+";");
			cg.insertCode("PRINTN \"Index non positive\" ");
			cg.insertCode("MOV AH, 4Ch");
			cg.insertCode("INT 21h");
			cg.insertCode("Greater"+System.identityHashCode(this)+":");
			
		} catch (TableException | ClassCastException e) {
			throw new CodeAlgorithmicError(e.getMessage());
		}
		
		return this.id+"[BX]";
	}
}
