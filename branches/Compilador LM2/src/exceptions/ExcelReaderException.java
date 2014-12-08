package exceptions;

/**Clase para una excepcion usando un lector de archivos de texto
 * @author Santy
 */
public class ExcelReaderException extends Exception{
	
	private static final long serialVersionUID = -1625644260431114976L;

	public ExcelReaderException(){
		super("Excepcion al intentar abrir o leer de un xls");
	}
	
	public ExcelReaderException(String s){
		super(s);
	}
	
	public ExcelReaderException(String s, Throwable cus){
		super(s,cus);
	}
	
	public String toString(){
		return("InstanceTokenException\nMessage: " + this.getMessage() + "\nStack: " + this.getStackTrace());
	}
}
