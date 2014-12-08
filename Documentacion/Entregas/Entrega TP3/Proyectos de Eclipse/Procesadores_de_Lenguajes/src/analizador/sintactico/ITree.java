package analizador.sintactico;


public interface ITree {
	
	/** Inserta en el Arbol una hoja Token
	 * @param t - Token a Insertar */
	public void ident();
	
	/** Inserta en el Arbol un Nodo Produccion 
	 * @param p - Produccion a Insertar */
	public void deident();
	
	public void insertar(String s);
	
	/** Cierra el Archivo del Arbol */
	public void close();
}
