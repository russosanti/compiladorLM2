package analizador.lexico.tokens;

public class OperadorBooleano extends Symbolo{
	
	public OperadorBooleano(int fila, int columna, String symbol) {
		super(fila, columna, symbol);
		if(esOperadorBooleanoN(symbol)){
			this.tipo = TokenTypes.OPERADOR_BOOLEANO_N;
		}else{
			this.tipo = TokenTypes.OPERADOR_BOOLEANO;
		}
	}
	
	/** @param s 	String con el lexema a evaluar
	 * @return true si es OperadorBooleano */
	public static boolean esOperadorBooleano(String s) {
		if(
				s.equals("=") ||
				s.equals("<") ||
				s.equals(">") ||
				s.equals("<=")||
				s.equals(">=") ||
				s.equals("<>"))
				return true;
		else
				return false;
	}
	
	public static boolean esOperadorBooleanoN(String s){
		if(
				s.equals("<") ||
				s.equals(">") ||
				s.equals("<=")||
				s.equals(">="))
				return true;
		else
				return false;
	}
}
