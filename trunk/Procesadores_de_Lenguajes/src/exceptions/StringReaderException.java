package exceptions;

/**Clase para una excepcion usando un lector de archivos de texto
 * @author Santy
 */
public class StringReaderException extends Exception{

	private static final long serialVersionUID = -1240255633624558793L;
	
	public StringReaderException(){
		super("Excepcion al intentar abrir o leer de un buffer");
	}
	
	public StringReaderException(String s){
		super(s);
	}
	
	public StringReaderException(String s, Throwable cus){
		super(s,cus);
	}
	
	public String toString(){
		return("InstanceTokenException\nMessage: " + this.getMessage() + "\nStack: " + this.getStackTrace());
	}
}
