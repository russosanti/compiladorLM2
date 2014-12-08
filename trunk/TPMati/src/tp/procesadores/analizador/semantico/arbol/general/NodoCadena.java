package tp.procesadores.analizador.semantico.arbol.general;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;
import tp.procesadores.compilador.generadorcodigo.Temporal;

public class NodoCadena extends ClaseNodo {
	
	private static final long serialVersionUID = 1L;
	private String lexema;
	
	public NodoCadena(String lexema){
		this.setLexema(lexema);
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	
	@Override
	public Codigo generarCodigo (Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{ 
		Temporal temp = tempManager.getNewTemporal(codigo.getContexto());
		this.setLexema(this.getLexema().replace("\'", ""));
		codigo.appendCodigo(temp.getTmp() +"\tdw\t\"" + this.getLexema() +"\"\n" + 
							"PUSH\tOFFSET " + temp.getTmp() + "\n" +
							"PUSH\t" + (this.getLexema().length()) + "\n");
		return codigo;
	}
}
