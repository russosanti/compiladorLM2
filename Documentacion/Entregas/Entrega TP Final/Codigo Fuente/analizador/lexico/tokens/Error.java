package analizador.lexico.tokens;

/**
 *Token para guardar errores con sus respectivos codigos  
 */
public class Error extends Token {

	private final int codError;
	private String lexema;
	private String mensaje;
	
	public Error(int fila, int columna, String lex, int codigo) {
		this(fila,columna,lex,codigo,null);
	}

	public Error(int fila, int columna, String lex, int codigo, String mensaje) {
		super(fila, columna);
		codError=codigo;
		lexema = lex;
		this.strlex = lex;
		this.mensaje = mensaje;
		this.tipo = TokenTypes.ERROR;
	}

	public int getCodError() {
		return codError;
	}

	public String getLexema() {
		return lexema;
	}
	
	public String getMensaje(){
		return this.mensaje;
	}
	
	public String toString(){
		String s;
		if(this.mensaje != null){
			s = "Tipo: " + this.tipo + " Valor: " + this.lexema + " Coordenadas: " + this.coord.getY() + ";" + this.coord.getX() + "\nCodigo de Error: " + this.codError + "Mensaje de Error: " + this.mensaje;
		}else{
			s = "Tipo: " + this.tipo + " Valor: " + this.lexema + " Coordenadas: " + this.coord.getY() + ";" + this.coord.getX() + "\nCodigo de Error: " + this.codError;
		}
		return s;
	}
	
	@Override 
	public String getStringLex(){
		return this.lexema;
	}
}
