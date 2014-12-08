package analizador.lexico.tokens;

/**
 * Token para guardar Cadenas de caracteres encerradas entre comilla simple  
 *
 */
public class Cadena extends Token {
	private String lexema;
	
	public Cadena(int fila, int columna, String cadena) {
		super(fila, columna);
		lexema = cadena;
		this.strlex = lexema;
		this.tipo = TokenTypes.CADENA;
	}
	public String getLexema() {
		return lexema;
	}
	

	@Override 
	public String getStringLex(){
		return this.lexema;
	}
	


}
