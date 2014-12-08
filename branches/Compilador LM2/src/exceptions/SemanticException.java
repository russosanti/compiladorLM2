package exceptions;

import analizador.lexico.tokens.Token;

/**Clase para tirar una Excepcion ante la instanciacion de un Token
 * @author Santy
 */
public class SemanticException extends Exception{

	private static final long serialVersionUID = -2580272923534578788L;
	protected Token tok;
	protected String title = "";
	protected String tip = "Semantic Exception";

	public SemanticException(){
		super("Error Semantico");
		this.tok = null;
	}
	
	public SemanticException(Token t, String s){
		super(s);
		this.tok = t;
	}
	
	public SemanticException(Token t, String tit, String s){
		super(s);
		this.tok = t;
		this.title = tit;
	}
	
	public SemanticException(String s){
		super(s);
		this.tok = null;
	}
	
	public SemanticException(String tit, String s){
		super(s);
		this.tok = null;
		this.title = tit;
	}
	
	public SemanticException(String s, Exception e){
		super(s, e);
		this.tok = null;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String toString(){
		if(this.tok == null){
			return("\nSemantic Exception:\n" + this.title + "\nMessage: " + this.getMessage());
		}
		return("\n"+this.tip+"\n" + this.title + "\nMessage: " + this.getMessage() + "\nToken: " + this.tok);
	}
}
