package analizador.semantico.tree.exp;

import java.util.ArrayList;
import java.util.Iterator;

import exceptions.CodeAlgorithmicError;
import exceptions.CodeException;
import generadorCodigo.ParameterRetainer;
import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import tabladesimbolos.iTablaDeSimbolos;
import analizador.semantico.tree.Nodo;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoPasaje extends Nodo<NodoExp>{

	protected TipoNodo tipoNodo = TipoNodo.PASAJE;
	private ArrayList<Tipo> tipo;
	
	@Override
	public TipoNodo getTipoNodo() {
		return this.tipoNodo;
	}

	public ArrayList<Tipo> getTipos() {
		return tipo;
	}

	public void setTipos(ArrayList<Tipo> tipo) {
		this.tipo = tipo;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			Iterator<NodoExp> it = this.hijos.iterator();
			prin.ident();
			while(it.hasNext()){
				it.next().printTree();
			}
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " + this.id + " Tipo Nodo: " +this.tipoNodo;
	}
	
	@Override
	public void generateCode() throws CodeException {
		ITree cg = ExecutionTree.getInstance();
		ParameterRetainer pRetainer = ParameterRetainer.getInstance();
		int counter = 0;
		for (Iterator<NodoExp> iterator = this.hijos.iterator(); iterator.hasNext();){
			NodoExp exp = iterator.next();
			exp.generateCode();
			if(pRetainer.isParamByRef(counter)){
				try{
					NodoAcceso acc = (NodoAcceso)exp;
					iTablaDeSimbolos tabla = SingleTabla.getInstance();
					if(tabla.isGlobalContext()){
						cg.insertCode("LEA AX,"+acc.getId());
					}else{
						cg.insertCode("LEA AX,"+acc.getId()+tabla.getContexto());
					}
				}catch(ClassCastException cce){
					throw new CodeAlgorithmicError("Error en Nodo Pasaje","Error pasando el parametro numero "+counter+"a la funcion");
				}
			}
			cg.insertCode("push AX");
			counter++;
		}
	}
	
	public boolean isParamByRef(String id){
		ParameterRetainer pRetainer = ParameterRetainer.getInstance();
		int counter = 0;
		for (Iterator<NodoExp> iterator = this.hijos.iterator(); iterator.hasNext();){
			NodoExp exp = iterator.next();
			if(pRetainer.isParamByRef(counter)){
				try{
					NodoAcceso acc = (NodoAcceso)exp; 
					iTablaDeSimbolos tabla = SingleTabla.getInstance();
					if(id.equalsIgnoreCase(acc.getId()+tabla.getContexto())){
						return true;
					}
				}catch(ClassCastException cce){
					return false;
				}
			}
			counter++;
		}
		return false;
	}
}
