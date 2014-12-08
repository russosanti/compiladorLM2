package tp.procesadores.analizador.lexico.tokens.visitor;

import tp.procesadores.analizador.semantico.arbol.principal.Procedimiento;

public interface VisitableProcedimientoNode {

	public Procedimiento acceptProcVisitor(ProcedimientoNodeVisitor visitor);
}
