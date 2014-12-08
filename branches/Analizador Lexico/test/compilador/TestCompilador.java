package compilador;

import org.junit.Test;
import tabladesimbolos.SingleTabla;
import tabladesimbolos.TablaDeSimbolos;
import analizador.lexico.LexicAnalyzer;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;

public class TestCompilador {

	@Test
	//@Test(timeout=1000)
	public void test() {
		LexicAnalyzer la = new LexicAnalyzer("../Procesadores_de_Lenguajes/test/Test2");
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
