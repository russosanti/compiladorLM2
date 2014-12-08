package analizador.sintactico.estructuras;

import org.junit.Test;

public class TableActionTest {

	@Test //Passed
	public void test() {
		TableAction ta = new TableAction("s141414");
		System.out.println(ta.toString());
	}

}
