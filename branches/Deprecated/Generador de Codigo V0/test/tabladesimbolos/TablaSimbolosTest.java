package tabladesimbolos;

import java.util.ArrayList;

import org.junit.Test;

import exceptions.SemanticException;
import exceptions.TableException;

public class TablaSimbolosTest {

	@Test
	public void test() {
		TablaDeSimbolos t = new TablaDeSimbolos();

		try {
			t.addArray("A", Tipo.INTEGER, 10);
			t.addArray("B", Tipo.INTEGER, 10);
			t.addArray("C", Tipo.INTEGER, 10);
			t.addConstGlobales("D", Tipo.BOOLEAN, "false");
			ArrayList<Tipo> tip = new ArrayList<Tipo>();
			tip.add(Tipo.BOOLEAN);
			tip.add(Tipo.INTEGER);
			t.addProcGlobales("A", tip);
			t.addParam("U", Tipo.BOOLEAN, false);
			t.addVar("A", Tipo.INTEGER);
			t.addConst("B", Tipo.INTEGER, "10");
			
			tip = new ArrayList<Tipo>();
			tip.add(Tipo.BOOLEAN);
			tip.add(Tipo.INTEGER);
			t.addFuncGlobales("B", tip,Tipo.BOOLEAN);
			
			t.addVar("C", Tipo.INTEGER);
			t.addConst("Y", Tipo.INTEGER, "10");
			
			t.addProcGlobales("maina", null);
			
			if(t.hasMain()){
				System.out.println("Hay Main la puta madre!!");
			}else{
				System.out.println("No creaste main!!");
			}
			
			t.printTabla();
		} catch (SemanticException | TableException e) {
			System.out.println(e);
		}
	}

}
