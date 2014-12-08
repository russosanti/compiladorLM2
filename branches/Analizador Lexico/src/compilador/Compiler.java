package compilador;


import analizador.lexico.LexicAnalyzer;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;

/**
 * Clase principal del programa Compilador 
 */
public class Compiler {

	public static void main(String[] args) {
		if ( args.length < 1) {
			System.out.println("Este compilador del lenguaje LM2 precisa de 1 parametro: ");
		}else{
			LexicAnalyzer la = new LexicAnalyzer(args[0]);
			Token t = la.getToken();
			System.out.println(t);
			while(t.getType()!=TokenTypes.EOF){
				t = la.getToken();
				System.out.println(t);
			}
		}
	}
}
