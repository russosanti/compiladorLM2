package analizador.semantico.reglas;

import java.util.Stack;
import org.junit.Test;

public class StackTest {

	@Test
	public void test(){
		Stack<String> p = new Stack<String>();
		p.push("0");
		p.push("1");
		p.push("2");
		
		System.out.println(p.elementAt(0));
	}
}
