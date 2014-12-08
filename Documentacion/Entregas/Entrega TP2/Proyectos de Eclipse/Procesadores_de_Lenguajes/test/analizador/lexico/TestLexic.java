package analizador.lexico;

import org.junit.Test;
import tabladesimbolos.SingleTabla;
import tabladesimbolos.TablaDeSimbolos;
import analizador.lexico.LexicAnalyzer;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;

public class TestLexic {

	@Test //Passed
	//@Test(timeout=1000)
	public void test() {
		LexicAnalyzer la = new LexicAnalyzer("../Procesadores_de_Lenguajes/test/Test");
		Token t = la.getToken();
		System.out.println(t);
		while(t.getType()!=TokenTypes.EOF){
			t = la.getToken();
			System.out.println(t);
		}
		System.out.println("");
		TablaDeSimbolos tabla = SingleTabla.getInstance();
		tabla.showIDs();
	}

}
