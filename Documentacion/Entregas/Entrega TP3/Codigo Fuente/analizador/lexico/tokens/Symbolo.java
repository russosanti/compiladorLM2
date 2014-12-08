package analizador.lexico.tokens;

/**
 * Token para guardar Operadores, aritmeticos y del leguaje.  
 */
public class Symbolo extends Token {
	private String lexema;
	
	public Symbolo(int fila, int columna, String symbol, TokenTypes t) {
		super(fila, columna);
		lexema = symbol;
		this.strlex = symbol;
		this.tipo = t;
	}
	
	public Symbolo(int fila, int columna, String symbol) {
		super(fila, columna);
		lexema = symbol;
		this.strlex = symbol;
		this.tipo = getTipo(symbol);
	}
	
	public String getLexema() {
		return lexema;
	}
	
	
	public static TokenTypes getTipo(String s){
		TokenTypes t;
		switch(s){
			case "(":
				t = TokenTypes.PARENTESIS_APERTURA;
				break;
			case ")":
				t = TokenTypes.PARENTESIS_CIERRE;
				break;
			case "[":
				t = TokenTypes.CORCHETE_APERTURA;
				break;
			case "]":
				t = TokenTypes.CORCHETE_CIERRE;
				break;
			case ":":
				t = TokenTypes.DEF_TIPO;
				break;
			case ",":
				t = TokenTypes.COMMA;
				break;
			default:
				t = OperadorMatematico.TipoOperador(s);
				if(t == null){
					if(OperadorBooleano.esOperadorBooleano(s)){
						if(OperadorBooleano.esOperadorBooleanoN(s)){
							t = TokenTypes.OPERADOR_BOOLEANO_E;
						}else{
							t = TokenTypes.OPERADOR_BOOLEANO;
						}
					}else{
						t = TokenTypes.SYMBOLO;
					}
				}
		}
		return t;
	}
	
	@Override 
	public String getStringLex(){
		return this.lexema;
	}
}
