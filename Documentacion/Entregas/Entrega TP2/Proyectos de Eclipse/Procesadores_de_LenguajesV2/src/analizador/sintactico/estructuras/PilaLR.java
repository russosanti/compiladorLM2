package analizador.sintactico.estructuras;

import interfaz.UI;

import java.util.Iterator;
import java.util.Stack;

import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
 
 /** Implementacion de la Pila del algoritmo LR
  * @author Santy */
public class PilaLR implements IPilaLR{
	/** Pila fisica donde se almacenan los Apilables LR */
	Stack<LRApilable> pila;
	 
	 /** Creador de la pila con el estado 0*/
	public PilaLR(){
		this.pila = new Stack<LRApilable>();
		this.pila.push(new Estado(0));
	}
	
	/**Inserta en la pila a y dps s
	 * @param a el no terminal que lee de la entrada
	 * @param s el estado del automata
	 * @return true si no hay error, false si lo hay*/
	public boolean desplazar(Token a,Estado s){
		try{
			this.pila.push(a);
			this.pila.push(s);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**Inserta en la pila a y dps s
	 * @param a el no terminal que lee de la entrada
	 * @param s el estado del automata
	 * @return true si no hay error, false si lo hay*/
	public boolean desplazar(Token a,int s){
		return this.desplazar(a,new Estado(s));
	}
	
	/**Saca 2*B elementos de la pila e inserta A
	 * @param A Cantidad de elementos a insertar
	 * @param B cantidad de elementos que se sacan
	 * @return El estado que quedo en la cima de la cola con el cual tengo q contrarrestar A. O null
	 * si hay error */
	public Estado reducir(NoTerminal A, int b){
		try{
			for(int i = 1;i<=2*b;i++){
				this.pila.pop();
			}
			Estado aux = (Estado)this.pila.peek();
			//System.out.println("Stack: "+this.toString());
			this.pila.push(A);
			return aux;
		}catch(ClassCastException ce){
			UI.error("Erro al reducir en la pila, se debia leer un estado y se leyo: "+
					this.pila.peek().getClass(),ce.getMessage());
			return null;
		}catch(Exception e){
			return null;
		}
	}
	
	/**Saca 2*b elementos de la pila e inserta A
	 * @param A Cantidad de elementos a insertar
	 * @param B cantidad de elementos que se sacan
	 * @return true si pudo redurir sino false */
	public Estado reducir(String A, int b){
		return this.reducir(new NoTerminal(A), b);
	}
	
	/**Saca 2*b elementos de la pila e inserta A
	 * @param A Cantidad de elementos a insertar
	 * @param B cantidad de elementos que se sacan
	 * @return true si pudo redurir sino false */
	public Estado reducir(Produccion p){
		return this.reducir(new NoTerminal(p.getIzquierda()),p.getCantDerecha());
	}
	
	/** Inserta el estado s en la pila luego de reducir
	 * @param s estado a insertar
	 * @return true si puede, false si falla*/
	public boolean insertState(Estado s){
		try{
			this.pila.push(s);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/** Inserta el estado s en la pila luego de reducir
	 * @param s estado a insertar
	 * @return true si puede, false si falla*/
	public boolean insertState(int s){
		return this.insertState(new Estado(s));
	}
	
	/** Se fija y devuelve el Estado en el tope de la pila sin sacarlo
	 * @return Estado en el tope de la pila */
	public LRApilable peek(){
		return this.pila.peek();
	}
	
	public void errorMode(){
		boolean continuar = true;
		LRApilable ea = null;
		while(continuar && this.pila.size()>1){
			if(this.pila.peek().getClass() == NoTerminal.class){
				continuar = false;
				this.pila.push(ea);
			}else{
				if(this.isToken(this.pila.peek())){
					Token t = (Token) this.pila.peek();
					if(t.getType() == TokenTypes.ENDLINE){
						continuar = false;
						this.pila.push(ea);
					}else{
						this.pila.pop();
					}
				}else{
					ea = this.pila.pop();
				}
			}
		}
	}
	
	public String toString(){
		String aux = "";
		Iterator<LRApilable> it = this.pila.iterator();
		while(it.hasNext()){
			aux = aux + " || " + it.next().toString(); 
		}
		return aux;
	}
	
	private boolean isToken(LRApilable ap){
		try{
			@SuppressWarnings("unused")
			Token t = (Token) ap;
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
