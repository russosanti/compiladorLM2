package analizador.sintactico.estructuras;

import org.junit.Test;

public class TablaTest {

	@Test//Passed!!
	public void test() {
		Tabla t = new Tabla("../Procesadores_de_Lenguajes/Gramatica/TablaSLR.xls");
		System.out.println(t.findAction(3, "$"));
	}

}
