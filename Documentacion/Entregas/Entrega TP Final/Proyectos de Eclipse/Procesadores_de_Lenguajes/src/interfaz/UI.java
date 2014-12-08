package interfaz;

import utils.SystemLog;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.sintactico.estructuras.Produccion;

/** Clase de la Interfaz de Usuario para que muestre por pantalla
 * @author Santy */
public class UI {
	
	/** Informa un Desplazamiento en Pantalla
	 * @param t - Token a Mostrar*/
	public static void informarDesplazamieto(Token t){
		if(t.getType() == TokenTypes.ENDLINE || t.getType() == TokenTypes.BEGIN ||
				t.getType() == TokenTypes.DO || t.getType() == TokenTypes.THEN){
			System.out.println(t.getStringLex());
		}else{
			System.out.print(t.getStringLex()+" ");
		}
	}
	
	/** Informa de una Reduccion
	 * @param p
	 */
	public static void informarReduccion(Produccion p){
		
	}
	
	/** Informa por Pantalla
	 * @param s - String a mostrar */
	public static void informar(String s){
		System.out.println(s);
	}
	
	/** Informa por Pantalla
	 * @param title - Titulo del Informe
	 * @param s - Informe */
	public static void informar(String title, String s){
		System.out.println(title + ": " + s);
	}
	
	/** Muestra un Error por Pantalla y lo loguea
	 * @param s - Error a Mostrar */
	public static void error(String s){
		System.out.println(s);
		System.out.println();
		SystemLog.getInstance().log_error(s);
	}
	
	/** Muestra un Error por Pantalla y lo Loguea
	 * @param title - Titulo del Error a Mostrar
	 * @param s - Error a Mostrar */
	public static void error(String title, String s){
		System.out.println(title + ": " + s);
		System.out.println();
		SystemLog.getInstance().log_error(title + ": " + s);
	}
	
	/** Mustra un Error Por Pantalla
	 * @param tipo - Tipo de Error
	 * @param s - Error a Mostrar
	 * @param t - Token que causo el Error
	 */
	public static void error(ErrorTypes tipo, String s, Token t){
		System.out.println(tipo.desc() + " en Fila: " + t.getFila() + " Columna: " + t.getColumna()+
		"|| Lexema: '" + t.getStringLex() +"' .Descripcion del error: "+s);
		System.out.println();
		SystemLog.getInstance().log_error(tipo.desc() + " en Fila: " + t.getFila() + " Columna: " + t.getColumna()+
				"|| Lexema: '" + t.getStringLex() +"' .Descripcion del error: "+s);
	}
	
	/** Muestra un Error Por Pantalla
	 * @param tipo - Tipo de Error
	 * @param t - Token que causo el Error
	 */
	public static void error(ErrorTypes tipo, Token t){
		System.out.println(tipo.desc() + " en Fila: " + t.getFila() + " Columna: " + t.getColumna()+
		"|| Lexema: " + t.getStringLex()+"'");
		System.out.println();
		SystemLog.getInstance().log_error(tipo.desc() + " en Fila: " + t.getFila() + " Columna: " + t.getColumna()+
				"|| Lexema: " + t.getStringLex()+"'");
	}
	
	/** Enum para los tipos de errores
	 * @author Santy */
	public enum ErrorTypes{
		LEXICERROR("Error Lexico"),
		SINTACTICERROR("Error Sintactico");
		
		private String desc;
		
		private ErrorTypes(String x){
			this.desc = x;
		}
		
		public String desc(){
			return this.desc;
		}
	}
	
	public static void exit(){
		System.exit(0);
	}
	
	public static void exit(int i){
		System.exit(i);
	}
}
