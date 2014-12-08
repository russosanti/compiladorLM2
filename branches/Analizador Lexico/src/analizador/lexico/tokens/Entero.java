package analizador.lexico.tokens;

import analizador.lexico.tokens.visitor.TokensVisitor;
/**
 * Token para guardar numeros enteros 
 */
public class Entero extends Token {
	private int lexema;
	private boolean positivo;
	public Entero(int fila, int columna, int lex) {
		super(fila, columna);
		lexema = lex;
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
	
	public String toString(){
		return ("Tipo: " + this.tipo + " Valor: " + this.lexema + " Coordenadas: " + this.coord.getY() + ";" + this.coord.getX());
	}
	
	@Override
	public String accept(TokensVisitor visitor) 
	{
		return visitor.visit(this);
	}
	
	
}
