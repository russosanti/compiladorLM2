package analizador.lexico.tokens;

import analizador.lexico.tokens.visitor.TokensVisitor;
/**
 * Token para guardar numeros Naturales
 */
public class Booleano extends Token {
	private boolean lexema;
	
	public Booleano(int fila, int columna, boolean booleano) {
		super(fila, columna);
		lexema = booleano;
		this.tipo = TokenTypes.BOOLEANO;
	}
	public boolean getLexema() {
		return lexema;
	}
	
	public String toString(){
		return ("Tipo: " + this.tipo + " Valor: " + this.lexema + " Coordenadas: " + this.coord.getY() + ";" + this.coord.getX());
	}
	
	@Override
	public String accept(TokensVisitor visitor){
		return visitor.visit(this);
	}
}
