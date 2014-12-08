package analizador.lexico.tokens;

import exceptions.InstanceTokenException;
import tabladesimbolos.SingleTabla;
import tabladesimbolos.TablaDeSimbolos;
import analizador.lexico.tokens.visitor.TokensVisitor;

/**
 * Token para guardar Palabras Reservadas del lenguaje  
 */
public class PalabraReservada extends Token{
	private String lexema;
	
	/**Este constructor genera un Token del Tipo que se le pasa por parametro
	 * @param fila
	 * @param columna
	 * @param palabra
	 * @param t */
	public PalabraReservada(int fila, int columna, String palabra, TokenTypes t) {
		super(fila, columna); 
		lexema = palabra;
		this.tipo = t;
	}
	 /**Este constructor detecta el tipo automaticamente
	  * @param fila
	  * @param columna
	  * @param palabra
	  * @throws InstanceTokenException - Si el tipo no es reconocido, lanza esta excepcion
	  */
	public PalabraReservada(int fila, int columna, String palabra) throws InstanceTokenException {
		super(fila, columna); 
		lexema = palabra;
		TokenTypes t = getTipo(palabra);
		if(t == TokenTypes.ERROR){
			throw new InstanceTokenException("Parser Error. PlabraReservada recibio un lexema que no puede aceptar como tal");
		}
		this.tipo = getTipo(palabra);
	}
	
	public String getLexema() {
		return lexema;
	}
	
	public String toString(){
		return ("Tipo: " + this.tipo + " Valor: " + this.lexema + " Coordenadas: " + this.coord.getY() + ";" + this.coord.getX());
	}
	
	public static TokenTypes getTipo(String s){
		TokenTypes t;
		s  = s.toLowerCase();
		switch(s){
			case "boolean":
				t = TokenTypes.TIPO;
				break;
			case "integer":
				t = TokenTypes.TIPO;
				break;
			case "and":
				t = TokenTypes.AND;
				break;
			case "begin":
				t = TokenTypes.BEGIN;
				break;
			case "byval":
				t = TokenTypes.BY;
				break;
			case "byref":
				t = TokenTypes.BY;
				break;
			case "const":
				t = TokenTypes.CONST;
				break;
			case "do":
				t = TokenTypes.DO;
				break;
			case "else":
				t = TokenTypes.ELSE;
				break;
			case "end-func":
				t = TokenTypes.END_FUNC;
				break;
			case "end-if":
				t = TokenTypes.END_IF;
				break;
			case "end-proc":
				t = TokenTypes.END_PROC;
				break;
			case "end-while":
				t = TokenTypes.END_WHILE;
				break;
			case "function":
				t = TokenTypes.FUNCTION;
				break;
			case "if":
				t = TokenTypes.IF;
				break;
			case "not":
				t = TokenTypes.NOT;
				break;
			case "or":
				t = TokenTypes.OR;
				break;
			case "procedure":
				t = TokenTypes.PROCEDURE;
				break;
			case "read":
				t = TokenTypes.READ;
				break;
			case "show":
				t = TokenTypes.SHOW;
				break;
			case "showln":
				t = TokenTypes.SHOW;
				break;
			case "then":
				t = TokenTypes.THEN;
				break;
			case "var":
				t = TokenTypes.VAR;
				break;
			case "while":
				t = TokenTypes.WHILE;
				break;
			default:
				TablaDeSimbolos tabla = SingleTabla.getInstance();
				if(tabla.searchPalabraReservada(s) != null){
					t = TokenTypes.PALABRARESERVADA;
				}else{
					t = TokenTypes.ERROR;
				}
		}
		return t;
	}
	
	@Override
	public String accept(TokensVisitor visitor) 
	{
		return visitor.visit(this);
	}
}
