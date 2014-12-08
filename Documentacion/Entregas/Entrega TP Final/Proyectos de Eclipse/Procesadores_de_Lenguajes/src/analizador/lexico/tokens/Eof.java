package analizador.lexico.tokens;

/**
 * Token para guardar la marca de fin de archivo EOF 
 */
public class Eof extends Token {
	
	private String lexema; 
	public Eof(int fila, int columna) {
		super(fila, columna);
		lexema="EoF";
		this.strlex = "EoF";
		this.tipo = TokenTypes.EOF;
	}
	
	public String getLexema() {
		return lexema;
	}
	
	public String toString(){
		return (" Valor: " + this.lexema + " Coordenadas: " + this.coord.getY() + ";" + this.coord.getX());
	}
	
	@Override 
	public String getStringLex(){
		return this.lexema;
	}
}
