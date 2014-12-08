package analizador.lexico.tokens;

/**
 * Clase que representa el token de Tipo
 * */
public class Tipo extends Token{
	
	private String lexema; 
	public Tipo(int fila, int columna, String palabra) {
		super(fila, columna); 
		lexema = palabra;
		this.strlex = palabra;
		this.tipo = TokenTypes.TIPO;
	}
	
	public String getLexema() {
		return lexema;
	}
	
	@Override 
	public String getStringLex(){
		return this.lexema;
	}
}
