package analizador.sintactico.estructuras;


import org.junit.Test;

import analizador.sintactico.estructuras.Gramatica;

public class GrammarTest {

	@Test //Passed
	public void test() {
		Gramatica g = new Gramatica("../Procesadores_de_Lenguajes/Gramatica/Gramatica.grammar");
		g.showGrammar();
	}

}
