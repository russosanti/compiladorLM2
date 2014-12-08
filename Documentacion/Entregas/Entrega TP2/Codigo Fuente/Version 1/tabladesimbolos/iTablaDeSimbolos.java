package tabladesimbolos;

import analizador.lexico.tokens.ID;
import analizador.lexico.tokens.TokenTypes;

/** Interfaz para la Tabla de Simbolos
 * @author Santy */
public interface iTablaDeSimbolos {
	
	public boolean addIdentificador(ID p);
	public boolean searchAll(String s);
	public TokenTypes searchPalabraReservada(String palabra);
	public boolean searchIdentificador(String s);
}
