package analizador.lexico.tokens;

import analizador.lexico.tokens.visitor.TokensVisitor;

public class Tipo extends Token{
	
	private String lexema; 
	public Tipo(int fila, int columna, String palabra) {
		super(fila, columna); 
		lexema = palabra;
		this.tipo = TokenTypes.TIPO;
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
