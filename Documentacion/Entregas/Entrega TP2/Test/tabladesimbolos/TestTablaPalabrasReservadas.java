package tabladesimbolos;

import org.junit.Test;

public class TestTablaPalabrasReservadas {

	@Test
	public void test() {
		TablaPalabrasReservadas t = new TablaPalabrasReservadas();
		if(t.existPalabraReservada("if")){
			System.out.println("existe");
		}else{
			System.out.println("no");
		}
	}

}
