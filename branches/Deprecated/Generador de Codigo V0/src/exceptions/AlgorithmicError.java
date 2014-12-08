package exceptions;

/**Clase para tirar una Excepcion ante la instanciacion de un Token
 * @author Santy
 */
public class AlgorithmicError extends GeneralError{

	private static final long serialVersionUID = 3446879588942118202L;

	public AlgorithmicError(){
		super("Error Sintactico");
	}
	
	public AlgorithmicError(String s){
		super(s);
	}
	
	public AlgorithmicError(String tit, String s){
		super(tit,s);
	}
	
	public AlgorithmicError(String s, Exception e){
		super(s, e);
	}
	
	public AlgorithmicError(String tit, String s, Exception e){
		super(tit, s, e);
	}
}
