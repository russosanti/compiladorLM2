package analizador.sintactico;


public interface ITree {
	
	/** Inserta en el Arbol una hoja Token
	 * @param t - Token a Insertar */
	public void ident();
	
	/** Inserta en el Arbol un Nodo Produccion 
	 * @param p - Produccion a Insertar */
	public void deident();
	
	public void insertar(String s);
	
	/** Inserta en el Arbol un Nodo Produccion 
	 * @param p - Produccion a Insertar */
	public void insertCode(String s);
	
	/** Cierra el Archivo del Arbol */
	public void close();
	
	/** Inserta en el Arbol una hoja Token
	 * @param t - Token a Insertar */
	public void codeIdent();
	/** Inserta en el Arbol una hoja Token
	 * @param t - Token a Insertar */
	public void codeDeident();
}
