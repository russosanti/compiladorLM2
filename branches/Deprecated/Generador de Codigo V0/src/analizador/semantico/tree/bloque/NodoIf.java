package analizador.semantico.tree.bloque;

import java.util.ArrayList;
import java.util.Iterator;

import exceptions.CodeException;
import analizador.semantico.tree.exp.NodoExp;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoIf extends NodoWhile{
	
	protected ArrayList<Sentencias> bloqueSi;
	
	public NodoIf(NodoExp condicion, ArrayList<Sentencias> bloque, ArrayList<Sentencias> bloqueSi){
		super(condicion,bloque);
		this.bloqueSi = bloqueSi;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOIF;
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
			it = this.bloqueSi.iterator();
			while(it.hasNext()){
				it.next().printTree();
			}
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo();
	}
	
	@Override
	public void generateCode() throws CodeException {
		
		ITree cg = ExecutionTree.getInstance();
		
		this.condicion.generateCode(); //Genero el código de la condición
		
		cg.insertCode("cmp ax,1");
		
		cg.insertCode("jne ElseBlk"+System.identityHashCode(this.bloqueSi)+";");
		
		//Agrego las sentencias del bloque
		for (Iterator<Sentencias> iterator = bloque.iterator(); iterator.hasNext();) {
			Sentencias sentencia = (Sentencias) iterator.next();
			sentencia.generateCode();
		}
		
		cg.insertCode("jmp EndOfIf"+System.identityHashCode(this)+";");
		
		//Agrego las sentencias del else
		cg.insertCode("ElseBlk"+System.identityHashCode(this.bloqueSi)+":");
		for (Iterator<Sentencias> iterator = bloqueSi.iterator(); iterator.hasNext();) {
			Sentencias sentencia = (Sentencias) iterator.next();
			sentencia.generateCode();
		}
		
		cg.insertCode("EndOfIf"+System.identityHashCode(this)+":");
		
	}
}
