package exceptions;

import analizador.lexico.tokens.Token;

/**Clase para tirar una Excepcion ante la instanciacion de un Token
 * @author Santy
 */
public class TypeException extends SemanticException{

	private static final long serialVersionUID = -5380109673996668015L;

	public TypeException(){
		super("Error de Tipo");
	}
	
	public TypeException(Token t, String s){
		super(t,s);
	}
	
	public TypeException(Token t, String tit, String s){
		super(t,s,tit);
	}
	
	public TypeException(String s){
		super(s);
	}
	
	public TypeException(String tit, String s){
		super(s,tit);
	}
	
	public TypeException(String s, Exception e){
		super(s, e);
	}
	
	public String toString(){
		if(this.tok == null){
			return("\nType Exception:\n" + this.title + "\nMessage: " + this.getMessage());
		}
		return("\nType Exception\n" + this.title + "\nMessage: " + this.getMessage() + "\nToken: " + this.tok);
	}
}
