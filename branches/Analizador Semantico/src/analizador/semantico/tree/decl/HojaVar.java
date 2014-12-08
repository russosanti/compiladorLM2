package analizador.semantico.tree.decl;

import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;
import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import tabladesimbolos.iTablaDeSimbolos;

public class HojaVar extends HojaDecl {
	
	private String contexto;
	
	public HojaVar(String s, Tipo t) {
		super(s, t);
		//Carga el contexto para HojaVar y para todos sus hijos
		iTablaDeSimbolos tb = SingleTabla.getInstance();
		this.setContexto(tb.getContexto());
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

	public String getContexto() {
		return contexto;
	}

	public void setContexto(String contexto) {
		this.contexto = contexto;
	}
}
