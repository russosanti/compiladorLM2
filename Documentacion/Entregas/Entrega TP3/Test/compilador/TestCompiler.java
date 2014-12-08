package compilador;

import static org.junit.Assert.*;
import org.junit.Test;
import analizador.sintactico.SintacticAnalyzer;

public class TestCompiler {

	@Test
	public void test(){
		SintacticAnalyzer sa = new SintacticAnalyzer("../Procesadores_de_Lenguajes/test/Test");
		int t = sa.compile();
		assertEquals(t,0);
	}
}
