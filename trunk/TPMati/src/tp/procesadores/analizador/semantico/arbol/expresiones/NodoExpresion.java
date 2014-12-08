package tp.procesadores.analizador.semantico.arbol.expresiones;

import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class NodoExpresion extends ClaseNodo {
	private static final long serialVersionUID = 1L;
		

	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{
		return this.nodos.get(0).generarCodigo(codigo, tempManager, labelManager);
	}
}