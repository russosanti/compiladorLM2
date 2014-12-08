package tp.procesadores.analizador.semantico.arbol.palres;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class Leer extends ClaseNodo {

	private static final long serialVersionUID = 1L;

	@Override
	public Codigo generarCodigo (Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{ 
		codigo.appendCodigo("PUSH\t0000h\n");
		codigo.appendCodigo("PUSH\t0001h\n");
		codigo.appendCodigo("CALL\treadln\n");
		codigo.appendCodigo("POP\tAX\n");
		Codigo codigoAux = new Codigo(); 
		codigoAux = this.nodos.get(0).generarCodigo(codigoAux, tempManager, labelManager);
		codigo.appendCodigo("MOV\t"+codigoAux.getLabel()+", AX\n");
		return codigo;
	}
}
