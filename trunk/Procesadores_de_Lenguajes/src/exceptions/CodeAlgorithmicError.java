package exceptions;

public class CodeAlgorithmicError extends AlgorithmicError{
	
	private static final long serialVersionUID = 6265037734180303322L;

	public CodeAlgorithmicError(){
		super("Error Sintactico");
	}
	
	public CodeAlgorithmicError(String s){
		super(s);
	}
	
	public CodeAlgorithmicError(String tit, String s){
		super(tit,s);
	}
	
	public CodeAlgorithmicError(String s, Exception e){
		super(s, e);
	}
	
	public CodeAlgorithmicError(String tit, String s, Exception e){
		super(tit, s, e);
	}
}
