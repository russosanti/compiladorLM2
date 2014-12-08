package analizador.lexico.tokens;

/**
 * Token para guardar numeros enteros 
 */
public class Entero extends Token {
	private int lexema;
	private boolean positivo;
	public Entero(int fila, int columna, int lex) {
		super(fila, columna);
		lexema = lex;
		this.strlex = String.valueOf(lex);
		this.tipo = TokenTypes.NUMERO;
		this.positivo = true;
		if(this.lexema<0){
			this.positivo = false;
		}
	}
	
	public int getLexema() {
		return lexema;
	}
	
	public boolean esPositivo(){
		return this.positivo;
	}
}
