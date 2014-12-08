package exceptions;

public class TableException extends MyException{

	private static final long serialVersionUID = 1034689043358010768L;
	private String title = "Excepcion de la Tabla de Simbolos";
	
	public TableException(){
		super("Excepcion");
	}
	
	public TableException(String s){
		super(s);
	}
	
	public TableException(String tit, String s){
		super(s);
		this.title = tit;
	}
	
	public TableException(String s, Exception e){
		super(s, e);
	}
	
	
	public String toString(){
		if(this.getCause()!=null){
			return("\n"+this.title+"\n" + this.title + "\nMessage: " + this.getMessage()+ "\nCausa: "+this.getCause().getMessage());
		}
		return("\n"+this.title+"\n" + this.title + "\nMessage: " + this.getMessage());
	}
}
