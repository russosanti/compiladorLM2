package analizador.lexico.tokens;

import analizador.lexico.Coordenadas;
import analizador.sintactico.estructuras.LRApilable;

/**
 * Token Generico  
 */
public abstract class Token implements LRApilable{
	protected Coordenadas coord;
	protected TokenTypes tipo;
	protected String strlex;
	
	public Token(int fila, int columna) {
		coord = new Coordenadas(columna,fila);
	}
	
	public TokenTypes getType(){
		return this.tipo;
	}
	
	public Coordenadas getCoordenadas(){
		return coord;
	}
	
	public int getFila(){
		return coord.getY();
	}
	
	public int getColumna(){
		return coord.getX();
	}
	
	public String toString(){
		return (this.getStringLex());
	}

	public String getStringLex(){
		return this.strlex;
	}
}
