package analizador.lexico.tokens;

/**
 * Token para guardar numeros Naturales
 */
public class Booleano extends Token {
	private boolean lexema;
	
	public Booleano(int fila, int columna, boolean booleano) {
		super(fila, columna);
		lexema = booleano;
		this.strlex = String.valueOf(booleano);
		this.tipo = TokenTypes.BOOLEANO;
	}
	public boolean getLexema() {
		return lexema;
	}
	
	public String toString(){
		return (this.getStringLex());
	}
}
