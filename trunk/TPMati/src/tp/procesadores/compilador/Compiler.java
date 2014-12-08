package tp.procesadores.compilador;

import tp.procesadores.analizador.sintactico.SintacticAnalyzer;

/**
 * Clase principal del programa Compilador 
 */
public class Compiler {

	public static void main(String[] args) {
		if ( args.length < 1) {
			System.out.println("El Analizador Léxico del lenguaje LM2 precisa de 1 parametro: " +
					"\n\t1.- origen: archivo con el codigo fuente creado en LEB " +
					"\n\nEJ: java -jar compilador.jar .\\fuente.lm2 ");
		}else 
		{ 
			SintacticAnalyzer sa = new SintacticAnalyzer(args[0]); 
			sa.Compilar();
		}
	}
}
