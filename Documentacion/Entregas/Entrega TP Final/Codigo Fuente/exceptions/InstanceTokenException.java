package exceptions;

/**Clase para tirar una Excepcion ante la instanciacion de un Token
 * @author Santy
 */
public class InstanceTokenException extends Exception{

	private static final long serialVersionUID = -1240255633624558793L;
	
	public InstanceTokenException(){
		super("Excepcion al intentar instanciar un Token");
	}
	
	public InstanceTokenException(String s){
		super(s);
	}
	
	public String toString(){
		return("InstanceTokenException\nMessage: " + this.getMessage() + "\nStack: " + this.getStackTrace());
	}
}
