package analizador.lexico.tokens;

import analizador.lexico.tokens.visitor.TokensVisitor;
/**
 * Token para guardar Cadenas de caracteres encerradas entre comilla simple  
 *
 */
public class Cadena extends Token {
	private String lexema;
	
	public Cadena(int fila, int columna, String cadena) {
		super(fila, columna);
		lexema = cadena;
		this.tipo = TokenTypes.CADENA;
	}
	public String getLexema() {
		return lexema;
	}
	
	public String toString(){
		return ("Tipo: " + this.tipo + " Valor: " + this.lexema + " Coordenadas: " + this.coord.getY() + ";" + this.coord.getX());
	}
	
	@Override 
	public String accept(TokensVisitor visitor) {
			return visitor.visit(this);	
	}
	


}
