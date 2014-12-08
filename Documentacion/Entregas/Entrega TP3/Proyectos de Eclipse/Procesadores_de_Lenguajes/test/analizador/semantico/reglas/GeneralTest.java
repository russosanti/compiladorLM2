package analizador.semantico.reglas;

import java.lang.reflect.Constructor;

import org.junit.Test;

import analizador.semantico.reglas.bloque.BLOQUE1;
import analizador.sintactico.estructuras.NoTerminal;

public class GeneralTest {

	@Test
	public void test(){
		try{
			@SuppressWarnings("unchecked")
			Class<? extends NoTerminal> myClass = NoTerminal.getNoTermClass("PARAM");

			//Class[] types = {Double.TYPE, this.getClass()};
			Constructor<? extends NoTerminal> constructor = myClass.getConstructor();

			//Object[] parameters = {new Double(0), this};
			BLOQUE1 instance = (BLOQUE1) constructor.newInstance();
			
			System.out.println(instance.getNoterm());
			
			instance.accionSemantica(null);
		}catch(Exception e){
			System.out.println("Error");
		}
	}
}
