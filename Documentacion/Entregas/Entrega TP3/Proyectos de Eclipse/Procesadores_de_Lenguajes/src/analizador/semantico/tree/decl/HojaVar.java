package analizador.semantico.tree.decl;

import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;
import tabladesimbolos.Tipo;

public class HojaVar extends HojaDecl {
	
	public HojaVar(String s, Tipo t) {
		super(s, t);
	}

	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.HOJAVAR;
	}

	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" ID: "+this.getId() +" Tipo: "+this.getTipo();
	}
}
