package tp.procesadores.analizador.lexico.tokens.visitor;

import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;

public interface VisitableTablaDeSimbolos {

	public TablaDeSimbolos acceptTSVisitor(TablaSimbolosVisitor visitor);
}
