package analizador.lexico.tokens;

import exceptions.InstanceTokenException;

public class OperadorMatematico extends Symbolo {
	
	public OperadorMatematico(int fila, int columna, String symbol) throws InstanceTokenException {
		super(fila, columna, symbol);
		TokenTypes t = TipoOperador(symbol);
		if(t == null){
			throw new InstanceTokenException("Error al crear el Token OperadorMatematico. El operador " + symbol + " no es reconocido");
		}
		this.tipo = t;
	}
	
	/** @param s 	String con el lexema a evaluar
	 * @return true si es OperadorBooleano */
	public static boolean esOperadorMatematico(String s) {
		if(
				s.equals('+') ||
				s.equals('-') ||
				s.equals('*') ||
				s.equals('/'))
				return true;
		else
				return false;
	}
	
	/** Retorna que tipo de Operador es o null si no es ningun operador
	 * @param s - string a analizar
	 * @return TokenTypes SUMA_RESTA, MULT_DIV o null*/
	public static TokenTypes TipoOperador(String s){
		if(s.equals("+") || s.equals("-")){
			return TokenTypes.SUMA_RESTA;
		}else{
			if(s.equals("*") || s.equals("/")){
				return TokenTypes.MULT_DIV;
			}
		}
		return null;
	}
}
