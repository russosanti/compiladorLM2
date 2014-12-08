package exceptions;


public abstract class MyException extends Exception{
	
	private static final long serialVersionUID = -3716859636247445113L;
	protected String title = "";
	protected String tip = "";
	
	public MyException(){
		super("Excepcion");
	}
	
	public MyException(String s){
		super(s);
	}
	
	public MyException(String tit, String s){
		super(s);
		this.title = tit;
	}
	
	public MyException(String s, Exception e){
		super(s, e);
	}
	
	public String getTitle(){
		return this.title;
	}
	
	
	public String toString(){
		if(this.getCause()!=null){
			return("\n"+this.title+"\n" + this.title + "\nMessage: " + this.getMessage()+ "\nCausa: "+this.getCause().getMessage());
		}
		return("\n"+this.title+"\n" + this.title + "\nMessage: " + this.getMessage());
	}
}
