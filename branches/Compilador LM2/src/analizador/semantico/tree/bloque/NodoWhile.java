package analizador.semantico.tree.bloque;

import java.util.ArrayList;
import java.util.Iterator;

import exceptions.CodeException;
import analizador.semantico.tree.INodo;
import analizador.semantico.tree.exp.NodoExp;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoWhile implements INodo, Sentencias{
	protected NodoExp condicion;
	protected ArrayList<Sentencias> bloque;
	
	
	public NodoWhile(NodoExp condicion, ArrayList<Sentencias> bloque){
		this.condicion = condicion;
		this.bloque =bloque;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOWHILE;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.condicion.printTree();
			Iterator<Sentencias> it = this.bloque.iterator();
			while(it.hasNext()){
				it.next().printTree();
			}
			prin.deident();
		}catch(Exception e){
			
		}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo();
	}

	@Override
	public void generateCode() throws CodeException {
		
		ITree cg = ExecutionTree.getInstance();		

		cg.insertCode("While"+System.identityHashCode(this)+":");

		this.condicion.generateCode(); //Genero el código de la condición
		
		cg.insertCode("cmp ax,1");
		
		cg.insertCode("jne EndWhile"+System.identityHashCode(this)+";");
		
		//Agrego las sentencias del bloque
		for (Iterator<Sentencias> iterator = bloque.iterator(); iterator.hasNext();) {
			Sentencias sentencia = (Sentencias) iterator.next();
			sentencia.generateCode();
		}
		
		cg.insertCode("jmp While"+System.identityHashCode(this)+";");
		
		cg.insertCode("EndWhile"+System.identityHashCode(this)+":");
		
		
	}
}
