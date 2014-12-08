package analizador.semantico.tree.func;

import exceptions.CodeAlgorithmicError;
import exceptions.CodeException;
import exceptions.TableException;
import generadorCodigo.ParameterRetainer;
import tabladesimbolos.SingleTabla;
import tabladesimbolos.iTablaDeSimbolos;
import tabladesimbolos.declaraciones.DeclArray;
import tabladesimbolos.declaraciones.DeclParam;
import tabladesimbolos.declaraciones.Declaraciones;
import analizador.semantico.tree.INodo;
import analizador.semantico.tree.bloque.Sentencias;
import analizador.semantico.tree.exp.NodoExp;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoRead implements INodo,iFuncList,Sentencias{
	
	private String id;
	private NodoExp indice;
	private boolean isArray;
	
	
	public NodoRead(String id){
		this.id = id;
		this.isArray = false;
	}
	
	public NodoRead(String id, NodoExp indice){
		this.id = id;
		this.indice = indice;
		this.isArray = true;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOREAD;
	}

	@Override
	public boolean isInput() {
		return true;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			if(this.isArray){
				this.indice.printTree();
			}
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" ID: "+this.id + "Es Array: " + this.isArray;
	}
	
	@Override
	public void generateCode() throws CodeException {
		
		ITree cg = ExecutionTree.getInstance();				
		cg.insertCode("call SCAN_NUM");
		cg.insertCode("mov "+this.getValor()+",cx");
		cg.insertCode("PRINTN \"\"");
	}
	
	private String getValor() throws CodeException{
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		try {
			Declaraciones decl = tabla.get(this.id);
			switch(decl.getTipoDeclaracion()){
			case VAR:
				if(tabla.declaredIDinContext(this.id)){
					return decl.getID()+tabla.getContexto();
				}else{
					return decl.getID();
				}
			case CONST:
				throw new CodeException("Error Generando codigo","No se puede asignar un valor a una constante");
			case PARAM:
				DeclParam dp = (DeclParam)decl;
				ParameterRetainer pr = ParameterRetainer.getInstance();
				if(dp.isByRef()){
					ITree cg = ExecutionTree.getInstance();
					cg.insertCode("MOV BX, [bp+" + pr.getParamPos(dp.getID()) +"]");
					return "word ptr [BX]";
				}else{
					return "[bp+" + pr.getParamPos(dp.getID()) +"]";
				}
			case ARRAY:
				if(this.isArray){
					DeclArray arrDecl = (DeclArray)tabla.get(this.id);
					ITree cg = ExecutionTree.getInstance();
					this.indice.generateCode();		
					cg.insertCode("MOV BX, AX");
					int tamano = arrDecl.getTamano();
					cg.insertCode("CMP BX,"+tamano);			
					cg.insertCode("JL Less"+System.identityHashCode(this)+";");
					cg.insertCode("PRINTN \"Index out of bounds\" ");
					cg.insertCode("MOV AH, 4Ch");
					cg.insertCode("INT 21h");
					cg.insertCode("Less"+System.identityHashCode(this)+":");
					return this.id+"[BX]";
				}else{
					throw new CodeAlgorithmicError("Error en nodo Read","La variable no fue leida como Array");
				}
			default:
				throw new CodeAlgorithmicError("Tipo de declaracion no valida para Nodo Asig Acceso");
			}
		} catch (TableException e) {
			throw new CodeAlgorithmicError(e.getTitle(),e.getMessage());
		} catch (ClassCastException ice){
			throw new CodeAlgorithmicError("Error en el Nodo Asig Acceso",ice.getMessage());
		}
	}
}
