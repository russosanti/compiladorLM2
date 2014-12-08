package exceptions;

/**Clase para tirar una Excepcion ante la instanciacion de un Token
 * @author Santy
 */
public class TreeException extends Exception{

	private static final long serialVersionUID = 2261270641707416780L;

	public TreeException(){
		super("Excepcion al generar el Arbol de Ejecucion");
	}
	
	public TreeException(String s){
		super(s);
	}
	
	public TreeException(String s, Exception e){
		super(s, e);
	}
	
	public String toString(){
		return("TreeException\nMessage: " + this.getMessage() + "\nStack: " + this.getStackTrace());
	}
}
