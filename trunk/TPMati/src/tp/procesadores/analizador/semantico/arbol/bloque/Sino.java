package tp.procesadores.analizador.semantico.arbol.bloque;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class Sino extends ClaseNodo {

	private static final long serialVersionUID = 1L;
	
	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager){
		Codigo codigoAux = new Codigo(); 
		codigoAux = this.nodos.get(0).generarCodigo(codigoAux, tempManager, labelManager);
		codigo.appendCodigo(codigoAux.getCodigo());
		return codigo;
	}
}
