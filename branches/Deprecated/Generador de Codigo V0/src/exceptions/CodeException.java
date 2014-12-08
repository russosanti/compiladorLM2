package exceptions;

public class CodeException extends MyException{
	
	private static final long serialVersionUID = 3714280658124815479L;

	public CodeException(){
		super("Excepcion");
	}
	
	public CodeException(String s){
		super(s);
	}
	
	public CodeException(String tit, String s){
		super(s);
		this.title = tit;
	}
	
	public CodeException(String s, Exception e){
		super(s, e);
	}
}
