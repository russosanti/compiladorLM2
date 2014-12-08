package tp.procesadores.analizador.lexico.tokens.visitor;

import tp.procesadores.analizador.semantico.arbol.general.Identificador;
import tp.procesadores.analizador.semantico.arbol.general.NodoEntero;
import tp.procesadores.analizador.semantico.arbol.general.NodoNatural;

public class NodeVisitor implements NodeVisitorInterface {

	@Override
	public String visit(Identificador identificador) {
		return identificador.getLexema();
	}

	@Override
	public String visit(NodoEntero entero) {
		return entero.getLexema();
	}
	
	public String visit(NodoNatural natural) {
		return natural.getLexema();
	}
}

