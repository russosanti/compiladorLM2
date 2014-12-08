package analizador.lexico.tokens;

import analizador.lexico.Coordenadas;
import analizador.lexico.tokens.visitor.*;

/**
 * Token Generico  
 */
public abstract class Token implements Visitable {
	protected Coordenadas coord;
	protected TokenTypes tipo;
	
	public Token(int fila, int columna) {
		coord = new Coordenadas(columna,fila);
	}
	
	public TokenTypes getType(){
		return this.tipo;
	}
	
	public String toString(){
		return ("Coordenadas: " + this.coord.getY() + ";" + this.coord.getX());
	}

	@Override
	public String accept(TokensVisitor visitor){
		return visitor.visit(this);
	}
}
