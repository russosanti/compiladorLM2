package analizador.lexico.tokens.visitor;

import analizador.lexico.tokens.*;
import analizador.lexico.tokens.Error;

/**
 * La clase que implemente esta interface podra visitar a cualquier Token 
 */

public interface Visitor {
	
	public String visit(Token token);
	public String visit(Booleano bool);
	public String visit(Cadena cadena);
	public String visit(Entero entero);
	public String visit(Eof eof);
	public String visit(Error error);
	public String visit(Symbolo sym);
	public String visit(ID palabra);
	public String visit(OperadorBooleano op);
	public String visit(OperadorMatematico op);
	public String visit(PalabraReservada palabra);
	public String visit(Tipo tipo);
}
