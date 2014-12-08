package analizador.sintactico.estructuras;

/** Wrapper de los No Terminales que se pueden apilar en la Pila LR
 * @author Santy */
public class NoTerminal implements LRApilable {
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
}
