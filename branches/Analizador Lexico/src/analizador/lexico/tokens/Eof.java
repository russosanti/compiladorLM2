package analizador.lexico.tokens;

import analizador.lexico.tokens.visitor.TokensVisitor;
/**
 * Token para guardar la marca de fin de archivo EOF 
 */
public class Eof extends Token {
	
	private String lexema; 
	public Eof(int fila, int columna) {
		super(fila, columna);
		lexema="EoF";
		this.tipo = TokenTypes.EOF;
	}
	
	public String getLexema() {
		return lexema;
	}
	
	public String toString(){
		return ("Tipo: " + this.tipo + " Valor: " + this.lexema + " Coordenadas: " + this.coord.getY() + ";" + this.coord.getX());
	}
	
	@Override
	public String accept(TokensVisitor visitor) 
	{
		return visitor.visit(this);
	}
}
