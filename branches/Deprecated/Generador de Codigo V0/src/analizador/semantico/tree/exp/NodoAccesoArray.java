package analizador.semantico.tree.exp;

import exceptions.CodeAlgorithmicError;
import exceptions.CodeException;
import exceptions.TableException;
import generadorCodigo.TemporalManager;
import tabladesimbolos.SingleTabla;
import tabladesimbolos.iTablaDeSimbolos;
import tabladesimbolos.declaraciones.DeclArray;
import analizador.lexico.tokens.ID;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoAccesoArray extends NodoAcceso{
	
	private NodoExp indice;
	protected TipoNodo tipoNodo = TipoNodo.ACCESOARRAY;
	
	/** Constructor para el nodo
	 * @param id - Id de la variable
	 * @param indice - Posicion en el vector */
	public NodoAccesoArray(String id, NodoExp indice){
		super(id);
		this.indice = indice;
	}
	
	/** Constructor para le NodoAccessoArray
	 * @param id - Id de la variable
	 * @param indice - Posicion en el vector
	 * @param tkn - Tkn del ID */
	public NodoAccesoArray(String id, NodoExp indice, ID tkn){
		super(id,tkn);
		this.indice = indice;
	}

	public NodoExp getIndice() {
		return indice;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.indice.printTree();
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" ID: "+this.id;
	}
	
	@Override
	public void generateCode() throws CodeException {
		ITree cg = ExecutionTree.getInstance();
		cg.insertCode("MOV AX,"+this.getValor());
		if(this.needsTemporal()){
			TemporalManager tm = TemporalManager.getInstance();
			String temp = tm.getTemporal();
			this.setTemporalUsed(temp);
			cg.insertCode("MOV " + temp +",AX");
		}
	}
	
	@Override
	public String getValor() throws CodeException{
		ITree cg = ExecutionTree.getInstance();
		//this.indice.setNeedsTemporal();
		this.indice.generateCode();
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
