package analizador.lexico.tokens;

/**
 *Token para guardar Identificadores  
 */
public class ID extends Token {
	private String lexema;
	public ID(int fila, int columna, String palabra) {
		super(fila, columna);
		lexema = palabra;
		this.strlex = lexema;
		this.tipo = TokenTypes.ID;
	}
	public String getLexema() {
		return lexema;
	}
	
	@Override 
	public String getStringLex(){
		return this.lexema;
	}
} 