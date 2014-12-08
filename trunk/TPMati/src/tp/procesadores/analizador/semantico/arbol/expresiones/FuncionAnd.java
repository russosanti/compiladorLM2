package tp.procesadores.analizador.semantico.arbol.expresiones;

import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class FuncionAnd extends ClaseNodo {

	private static final long serialVersionUID = 1L;

	public FuncionAnd(ClaseNodo nodo1, ClaseNodo nodo2){
		this.add(nodo1);
		this.add(nodo2);
	}
	
	
	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{
		Codigo codigoAux = new Codigo(); 
		codigoAux = this.nodos.get(0).generarCodigo(codigoAux, tempManager, labelManager);
		codigoAux.appendCodigo(codigo.getLabel() + "\n"); 
		Codigo codigoAux2 = new Codigo();
		codigoAux2 = this.nodos.get(1).generarCodigo(codigoAux2, tempManager, labelManager);
		codigo.appendCodigo(codigoAux.getCodigo() + codigoAux2.getCodigo() );
		return codigo;
	}
}
