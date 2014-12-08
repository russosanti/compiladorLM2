package exceptions;

/**Clase para tirar una Excepcion ante la instanciacion de un Token
 * @author Santy
 */
public class GeneralError extends Error{

	private static final long serialVersionUID = 3446879588942118202L;
	private String title = "";

	public GeneralError(){
		super("Error!!");
	}
	
	public GeneralError(String s){
		super(s);
	}
	
	public GeneralError(String tit, String s){
		super(s);
		this.title = tit;
	}
	
	public GeneralError(String s, Exception e){
		super(s, e);
	}
	
	public GeneralError(String tit, String s, Exception e){
		super(s, e);
		this.title = tit;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String toString(){
		if(title==null){
			return("Error: " + this.getMessage() + "\nStack: " + this.getStackTrace());
		}
		return(this.title + ": " + this.getMessage() + "\nStack: " + this.getStackTrace());
	}
}
