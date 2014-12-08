package compilador;

import analizador.sintactico.SintacticAnalyzer;



/**
 * Clase principal del programa Compilador 
 */
public class Compiler {

	public static void main(String[] args) {
		if ( args.length < 1) {
			System.out.println("Este compilador del lenguaje LM2 precisa de 1 parametro: ");
		}else{
			SintacticAnalyzer sa = new SintacticAnalyzer(args[0]);
			int t = sa.compile();
			if(t == 0){
				System.out.println("Exito!!");
			}
		}
	}
}
