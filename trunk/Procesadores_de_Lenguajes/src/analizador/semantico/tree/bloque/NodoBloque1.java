package analizador.semantico.tree.bloque;

import java.util.ArrayList;

import exceptions.CodeException;
import analizador.semantico.tree.Nodo;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoBloque1 extends Nodo<Sentencias>{
	
	
	public NodoBloque1(ArrayList<Sentencias> t){
		super(t);
		this.tipoNodo = TipoNodo.NODOBLOQUE1;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOBLOQUE1;
	}
	
	@Override
	public void generateCode() throws CodeException {
		ITree cg = ExecutionTree.getInstance();
		cg.insertCode("; Comienzo de las Sentencias");
		cg.insertCode("MOV AX, 0; Para evitar el arrastre de Basura");
		super.generateCode();
		cg.insertCode("; Fin de las Sentencias");
	}
}
