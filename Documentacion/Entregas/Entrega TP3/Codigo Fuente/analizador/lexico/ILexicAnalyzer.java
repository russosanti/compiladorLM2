package analizador.lexico;

import analizador.lexico.tokens.Token;

public interface ILexicAnalyzer {
	/**   
	 * Esta funcion devuelve el siguiente token del archivo 
	 */
	public Token getToken();
}
