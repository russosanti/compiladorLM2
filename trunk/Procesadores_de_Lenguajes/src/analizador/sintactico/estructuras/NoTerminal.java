package analizador.sintactico.estructuras;

import interfaz.UI;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;
import analizador.semantico.reglas.ReglaSemantica;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;


/** Wrapper de los No Terminales que se pueden apilar en la Pila LR
 * @author Santy */
public abstract class NoTerminal implements LRApilable, ReglaSemantica{ //hacer abstracta dps
	/** Valor del no terminal */
	private String noterm;
	
	public NoTerminal(String n){
		this.noterm = n;
	}

	public String getNoterm() {
		return noterm;
	}

	public void setNoterm(String noterm) {
		this.noterm = noterm;
	}
	
	public String toString(){
		return this.noterm;
	}
	
	@SuppressWarnings("unchecked")
	public static Class<? extends NoTerminal> getNoTermClass(String s){
		try{
			return(Class<? extends NoTerminal>) Class.forName("analizador.semantico.reglas." + s);
		}catch(Exception e){
			try{
				return(Class<? extends NoTerminal>) Class.forName("analizador.semantico.reglas.bloque." + s);
			}catch(Exception e1){
				try{
					return(Class<? extends NoTerminal>) Class.forName("analizador.semantico.reglas.decl." + s);
				}catch(Exception e2){
					try{
						return(Class<? extends NoTerminal>) Class.forName("analizador.semantico.reglas.exp." + s);
					}catch(Exception e3){
						try {
							return(Class<? extends NoTerminal>) Class.forName("analizador.semantico.reglas.func." + s);
						} catch (Exception e4) {
							UI.error("Error buscando la clase " + s);
							UI.exit(1);
							return null;
						}
					}
				}
			}
		}
	}
	
	public static NoTerminal getInstanceOf(String s){
		try{
			Class<? extends NoTerminal> myClass = NoTerminal.getNoTermClass(s);

			@SuppressWarnings("rawtypes")
			Class[] types = {String.class};
			Constructor<? extends NoTerminal> constructor = myClass.getConstructor(types);

			Object[] parameters = {s};
			NoTerminal instance;
			instance = constructor.newInstance(parameters);
			return instance;
		}catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e){
			throw new AlgorithmicError("Error Algoritmico","Error al tratar de instanciar la clase " + s,e);
		}
	}
	
	public abstract boolean accionSemantica(Stack<LRApilable> prod)throws SemanticException;
}
