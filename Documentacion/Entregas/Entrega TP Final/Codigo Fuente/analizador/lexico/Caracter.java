package analizador.lexico;

import analizador.lexico.tokens.TokenTypes;

/**
 * Esta clase guarda el simbolo que se esta tratando y nos da cierta informacion sobre el 
 */
public class Caracter{
	
	private int simbolo = -1;
	private char caracter;

	/**
	 * @return 	Devuelve el simbolo (int) que contiene el objeto Caracter 
	 */
	public int getSimbolo() {
		return simbolo;
	}
	
	public char getCar(){
		return this.caracter;
	}

	/**
	 * Setter para el simbolo que se guardara en el objeto Caracter 
	 * @param caracter	simbolo a ser guardado 
	 */
	
	public void setSimbolo(int caracter) {
		this.simbolo = caracter;
		try{
			this.caracter = (char) caracter;
		}catch(Exception e){
			
		}
	}
	
	/**
	 * Esta funcion devuelve true si el simbolo en este objeto se considera ignorable 
	 * @return	true si es ignorable 
	 */
	public boolean esIgnorable(){
		return Caracter.Ignorable(this.getSimbolo());
	}
	
	public static boolean Ignorable(int simbol){
		boolean result = false;  
		if((simbol == ' ') || (simbol == '\n') || (simbol == '\t') || (simbol == '\r')
				|| (simbol == '{')){ 
			result = true;
		}
		return result;
	}
	
	/**
	 * Esta funcion devuelve el tipo de lexema a tratar segun TokenTypes
	 * 
	 *  @return
	 */
	public TokenTypes getTipo(){
		TokenTypes tipo = TokenTypes.ERROR;
		
		if(Character.isDigit(this.getSimbolo())){ //si viene un -40 lo trato en el operador
				tipo= TokenTypes.NUMERO;				
		}else{
			if (this.simbolo == '\''){
				tipo=TokenTypes.CADENA; 							
			}else{
				if (esOperador(this.simbolo)){
					tipo=TokenTypes.SYMBOLO;
				}else{
					if(this.simbolo == '('){
						tipo = TokenTypes.PARENTESIS_APERTURA;
					}else{
						if(this.simbolo == ')'){
							tipo = TokenTypes.PARENTESIS_CIERRE;
						}else{
							if(this.simbolo == ';'){    
								tipo = TokenTypes.ENDLINE;
							}else{
								if(this.simbolo == '['){
									tipo = TokenTypes.CORCHETE_APERTURA;
								}else{
									if(this.simbolo == ']'){
										tipo = TokenTypes.CORCHETE_CIERRE;
									}else{
										if( Character.isLetter(this.simbolo)){
											tipo=TokenTypes.ID;
										}else{
											if( this.simbolo == -1){
												tipo= TokenTypes.EOF;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return tipo; 
	}

	
	/**
	 * Esta funcion devuelve true si el simbolo en este objeto es una llave {, lo cual marca el 
	 * principio de un comentario en el codigo. 
	 * 
	 * @return	true si el simbolo es una llave { 
	 */
	
	public boolean esPpioComentario() {
		return Caracter.esComentario(this.simbolo);
	}
	
	public static boolean esComentario(int simbol){
		boolean result=false; 
		if(simbol == '{')
			result = true; 
		return result;
	}
	
	
	/**
	 * Este funcion privada se utiliza para ver si el caracter que se encuentra en el objeto caracter 
	 * es o forma parte de un Operador 
	 * 
	 * @param s	simbolo 
	 * @return	true si se considera operador 
	 */
	public static boolean esOperador( int s){
		boolean result=false; 
		if (( s == '=') || ( s == '+') || ( s == '*') || ( s == '-') || (s == '>') || ( s == '<') || 
		( s == '/') || ( s == ',') || ( s == ':')){
			result = true;
		}
		return result; 
	} 
}
