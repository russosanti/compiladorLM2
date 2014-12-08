package analizador.sintactico.helpers;

import org.junit.Test;

public class ExcelReaderTest {

	@Test//Passed!!
	public void test() {
		ExcelReader e = new ExcelReader("../Procesadores_de_Lenguajes/Gramatica/TablaSLR.xls");
		e.operSheet("Tabla");
		e.showSheet();
	}

}
