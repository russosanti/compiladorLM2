package tp.procesadores.analizador.lexico.tokens.visitor;

import tp.procesadores.analizador.semantico.arbol.general.Identificador;
import tp.procesadores.analizador.semantico.arbol.general.NodoEntero;
import tp.procesadores.analizador.semantico.arbol.general.NodoNatural;

public interface NodeVisitorInterface {

	public String visit(Identificador identificador);
	public String visit(NodoEntero entero);
	public String visit(NodoNatural natural);	
}
