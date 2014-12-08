package analizador.semantico.tree.exp;

import java.util.ArrayList;
import java.util.Iterator;

import tabladesimbolos.SingleTabla;
import exceptions.CodeException;
import generadorCodigo.ParameterRetainer;
import generadorCodigo.TemporalManager;
import analizador.lexico.tokens.ID;
import analizador.semantico.tree.bloque.Sentencias;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoLlamada extends NodoAcceso implements Sentencias{
	
	private NodoPasaje parametros;
	
	public NodoLlamada(String id, NodoPasaje pasaje){
		super(id);
		this.parametros = pasaje;
		this.setTipoNodo(TipoNodo.LLAMADA);
	}
	
	public NodoLlamada(String id, NodoPasaje pasaje,ID tkn){
		super(id,tkn);
		this.parametros = pasaje;
		this.setTipoNodo(TipoNodo.LLAMADA);
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.parametros.printTree();
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" ID: "+this.id;
	}
	
	@Override
	public void generateCode() throws CodeException {
		ITree cg = ExecutionTree.getInstance();
		ParameterRetainer pr = ParameterRetainer.getInstance();
		TemporalManager tm = TemporalManager.getInstance();
		pr.setFuncID(this.getId());
		
		//Guardo los temporales en el Stack
		int cantTemporales = tm.getCounter();
		cg.insertCode("; Muevo los Temporales al Stack");
		cg.insertCode("MOV bp, sp");
		cg.insertCode("MOV CX, bp");
		for(int i=1;i<=cantTemporales;i++){
			cg.insertCode("MOV BX, 0");
			cg.insertCode("MOV BX, [bp-2]");
			cg.insertCode("push BX");
			cg.insertCode("MOV bp, sp");
		}
		cg.insertCode("push CX");
		cg.insertCode("MOV bp, CX");
		//guardo las variables en el Stack
		cg.insertCode("");
		cg.insertCode("; Muevo las Variables al Stack");
		ArrayList<String> arr = pr.getDeclaredVars(SingleTabla.getInstance().getContexto());
		String var;
		for (Iterator<String> iterator = arr.iterator(); iterator.hasNext();) {
			var = iterator.next();
			if(this.parametros.isParamByRef(var)){
				cg.insertCode(";"+var+" es pasada por referencia");
			}else{
				cg.insertCode("MOV BX, 0");
				cg.insertCode("MOV BX, "+var);
				cg.insertCode("push BX");
			}
		}
		
		//LLamo a la funcion
		cg.insertCode("; Llamo a la funcion");
		this.parametros.generateCode();
		cg.insertCode("call "+this.id);
		cg.insertCode("MOV DX, AX ;Guardo el retorno en un Registro Seguro");
		cg.insertCode("MOV CX, bp");
		
		//Recupero las variables
		cg.insertCode("; Recupero las Variables del Stack");
		for(int i=arr.size()-1;i>=0;i--){
			var = arr.get(i);
			if(this.parametros.isParamByRef(var)){
				cg.insertCode(";"+var+" es pasada por referencia");
			}else{
				cg.insertCode("MOV bp, 0");
				cg.insertCode("MOV bp, sp");
				cg.insertCode("MOV BX, [bp]");
				cg.insertCode("pop [bp]");
				cg.insertCode("MOV bp, 0");
				cg.insertCode("MOV bp, sp ;Por errores internos del Emu");
				cg.insertCode("MOV "+var+", BX");
			}
		}
		
		//Recupero los temporales
		cg.insertCode("pop CX");
		cg.insertCode("MOV bp, CX");
		cg.insertCode("; Recupero los temporales del Stack");
		cg.insertCode("MOV sp, bp");
		
		cg.insertCode("; Fin del Recupero");
		cg.insertCode("MOV AX, 0 ;");
		cg.insertCode("MOV AX, DX ;Recupero el retorno");
		cg.insertCode("");
		// El valor de retorno queda siempre guardado en AX!!
		if(this.needsTemporal()){
			String temp = tm.getTemporal();
			this.setTemporalUsed(temp);
			cg.insertCode("MOV " + temp +",AX");
		}
	}
	
	public String getValor() throws CodeException{
		this.generateCode();
		if(this.needsTemporal()){
			return this.getTemporal();
		}else{
			return "AX";
		}
	}
}
