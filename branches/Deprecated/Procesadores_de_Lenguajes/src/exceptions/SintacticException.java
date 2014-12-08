package exceptions;

/**Clase para tirar una Excepcion ante la instanciacion de un Token
 * @author Santy
 */
public class SintacticException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3564473098147968075L;

	public SintacticException(){
		super("Error Sintactico");
	}
	
	public SintacticException(String s){
		super(s);
	}
	
	public SintacticException(String s, Exception e){
		super(s, e);
	}
	
	public String toString(){
		return("Sintactic Exception\nMessage: " + this.getMessage() + "\nStack: " + this.getStackTrace());
	}
}
