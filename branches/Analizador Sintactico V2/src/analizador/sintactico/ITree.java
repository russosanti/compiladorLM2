package analizador.sintactico;

import analizador.lexico.tokens.Token;
import analizador.sintactico.estructuras.Produccion;

public interface ITree {
	
	/** Inserta en el Arbol una hoja Token
	 * @param t - Token a Insertar */
	public void insertar(Token t);
	
	/** Inserta en el Arbol un Nodo Produccion 
	 * @param p - Produccion a Insertar */
	public void insertar(Produccion p);
	
	/** Cierra el Archivo del Arbol */
	public void close();
}
