package tp.procesadores.analizador.semantico.arbol.expresiones;

import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.Label;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class FuncionOr extends ClaseNodo {

	private static final long serialVersionUID = 1L;
	
	public FuncionOr(ClaseNodo nodo1, ClaseNodo nodo2){
		this.add(nodo1);
		this.add(nodo2);
	}

	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{
		Codigo codigoAux1 = new Codigo(); 
		codigoAux1.setLabel("or"); 
		codigoAux1 = this.nodos.get(0).generarCodigo(codigoAux1, tempManager, labelManager);
		Label continuar = labelManager.getNewLabel("CONTINUAR");
		codigoAux1.appendCodigo(continuar.getLabelName());
		Codigo codigoAux2 = new Codigo();
		codigoAux2.setLabel("");
		codigoAux2 = this.nodos.get(1).generarCodigo(codigoAux2, tempManager, labelManager);
		codigoAux2.appendCodigo(codigo.getLabel());
		codigo.appendCodigo(codigoAux1.getCodigo() + "\n" + 
							codigoAux2.getCodigo() + "\n" + 
							continuar.getLabelName() + ":\n");
		return codigo; 
	}
	
}
