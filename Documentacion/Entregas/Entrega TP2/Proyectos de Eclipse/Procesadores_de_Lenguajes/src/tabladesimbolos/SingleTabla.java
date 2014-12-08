package tabladesimbolos;

/**Devuelve la unica instancia de la tabla de Simbolos
 * @author Santy
 */
public class SingleTabla {
	private static TablaDeSimbolos instance = new TablaDeSimbolos();
	
	public static TablaDeSimbolos getInstance(){
		return instance;
	}
}
