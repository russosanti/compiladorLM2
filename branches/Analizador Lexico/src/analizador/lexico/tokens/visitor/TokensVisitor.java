package analizador.lexico.tokens.visitor;

import analizador.lexico.tokens.*;
import analizador.lexico.tokens.Error;

public class TokensVisitor implements Visitor{

	public String visit(Token token){
		return null; 
	}
	
	/** Vistita un objeto Natural y accede a su lexema
	 *  @param natural 	objeto a ser visitado */
	public String visit(Booleano bool){
		return Boolean.toString(bool.getLexema());
	}
	
	/** Vistita un objeto Cadena y accede a su lexema
	 *  @param cadena	objeto a ser visitado */
	public String visit(Cadena cadena){
		return cadena.getLexema(); 
	}

	/** Vistita un objeto Entero y accede a su lexema
	 *  @param Entero	objeto a ser visitado */
	public String visit(Entero entero){
		return Integer.toString(entero.getLexema());
	}
	
	/**Vistita un objeto Eof y accede a su lexema
	 * @param eof	objeto a ser visitado */
	public String visit(Eof eof) {
		return eof.getLexema(); 
	}
	
	/** Vistita un objeto Error y accede a su lexema y codigo de error
	 * @param error objeto a ser visitado */
	public String visit(Error error){
		return error.toString(); 
	}
	
	/** Vistita un objeto Operador y accede a su lexema
	 * @param operador objeto a ser visitado */
	public String visit(Symbolo sym){
		return sym.getLexema(); 
	}

	/** Vistita un objeto Palabra y accede a su lexema
	 * @param palabra	objeto a ser visitado */
	public String visit(ID palabra) {
		return palabra.getLexema(); 
	}
	
	/** Vistita un objeto Tipo y accede a su lexema
	 * @param tipo	objeto a ser visitado */
	public String visit(OperadorBooleano op) {
		return op.getLexema();
	}
	
	/** Vistita un objeto Tipo y accede a su lexema
	 * @param tipo	objeto a ser visitado */
	public String visit(OperadorMatematico op) {
		return op.getLexema();
	}

	/** Vistita un objeto PalabraReservada y accede a su lexema
	 * @param palabra	objeto a ser visitado */
	public String visit(PalabraReservada palabra) {
		return palabra.getLexema();
	}

	/** Vistita un objeto Tipo y accede a su lexema
	 * @param tipo	objeto a ser visitado */
	public String visit(Tipo tipo) {
		return tipo.getLexema();
	}
} 
