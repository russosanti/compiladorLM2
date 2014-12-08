package analizador.semantico.tree.decl;

import exceptions.CodeException;
import generadorCodigo.ParameterRetainer;
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

	@Override
	public void generateCode() throws CodeException {
		ITree cg = ExecutionTree.getInstance();
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		if(tabla.isGlobalContext()){
			ParameterRetainer pr = ParameterRetainer.getInstance();
			pr.addVars(tabla.getContexto(),this.getId());
			cg.insertCode(this.getId()+" DW 0");
		}else{
			ParameterRetainer pr = ParameterRetainer.getInstance();
			pr.addVars(tabla.getContexto(),this.getId()+tabla.getContexto());
			cg.insertCode(this.getId()+tabla.getContexto()+" DW 0");
		}
	}
}
