package analizador.semantico.tree.bloque;

import java.util.ArrayList;

import exceptions.CodeException;
import analizador.semantico.tree.Nodo;
import analizador.semantico.tree.decl.HojaDecl;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoGlobales extends Nodo<HojaDecl>{
	
	public NodoGlobales(){
		super();
		this.tipoNodo = TipoNodo.NODOGLOBALES;
	}
	
	public NodoGlobales(ArrayList<HojaDecl> t){
		super(t);
		this.tipoNodo = TipoNodo.NODOGLOBALES;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOGLOBALES;
	}
	
	@Override
	public void generateCode() throws CodeException {
		ITree cg = ExecutionTree.getInstance();
		cg.insertCode("");
		cg.insertCode("; Comienza la delcaracion de Var globales");
		super.generateCode();
		cg.insertCode("; Finaliza la delcaracion de Globales");
		cg.insertCode("");
	}
}
