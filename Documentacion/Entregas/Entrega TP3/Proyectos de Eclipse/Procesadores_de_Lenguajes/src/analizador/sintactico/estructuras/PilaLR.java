package analizador.sintactico.estructuras;

import interfaz.UI;

import java.util.Iterator;
import java.util.Stack;

import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.S;
import analizador.semantico.reglas.SPrima;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;
 
 /** Implementacion de la Pila del algoritmo LR
  * @author Santy */
public class PilaLR implements IPilaLR{
	/** Pila fisica donde se almacenan los Apilables LR */
	Stack<LRApilable> pila;
	Estado estadoAnt;
	 
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
			this.estadoAnt = s;
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
	 * si hay error 
	 * @throws SemanticException */
	public Estado reducir(NoTerminal A, int b) throws SemanticException{
		try{
			Stack<LRApilable> prodDerecha = new Stack<LRApilable>();
			LRApilable prod;
			for(int i = 1;i<=2*b;i++){
				prod = this.pila.pop();
				if(!this.isState(prod)){
					prodDerecha.push(prod);
				}
			}
			Estado aux = (Estado)this.pila.peek();
			this.estadoAnt = aux;
			//System.out.println("Stack: "+this.toString());
			try{
				if(!A.accionSemantica(prodDerecha)){
					throw new SemanticException();
				}
			}finally{
				this.pila.push(A);
				try{
					S s = (S)A;
					s.getArbol().printTree();
				}catch(ClassCastException ex){
					try{
						SPrima sp = (SPrima)A;
						sp.getArbol().printTree();
					}catch(ClassCastException ex1){

					}catch(NullPointerException e){
						throw new AlgorithmicError("El arbol no se imprimira debido a los errores de compilacion");
					}
				}catch(NullPointerException e){
					throw new AlgorithmicError("El arbol no se imprimira debido a los errores de compilacion");
				}
			}
			return aux;
		}catch(ClassCastException ce){
			UI.error("Erro al reducir en la pila, se debia leer un estado y se leyo: "+
					this.pila.peek().getClass(),ce.getMessage());
			return null;
		}
	}
	
	/**Saca 2*b elementos de la pila e inserta A
	 * @param A Cantidad de elementos a insertar
	 * @param B cantidad de elementos que se sacan
	 * @return true si pudo redurir sino false 
	 * @throws SemanticException */
	public Estado reducir(String A, int b) throws SemanticException{
			return this.reducir(NoTerminal.getInstanceOf(A),b);
	}
	
	/**Saca 2*b elementos de la pila e inserta A
	 * @param A Cantidad de elementos a insertar
	 * @param B cantidad de elementos que se sacan
	 * @return true si pudo redurir sino false 
	 * @throws SemanticException */
	public Estado reducir(Produccion p) throws SemanticException{
		return this.reducir(p.getIzquierda(),p.getCantDerecha());
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
	
	/** Se fija y devuelve el Estado en el que estaba la Pila, esto para que los Errores Semanticos
	 * no rompan el algoritmo de Desplazamiento Reduccion
	 * @return Estado anterior */
	public Estado estadoAnt(){
		return this.estadoAnt;
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
	
	private boolean isState(LRApilable ap){
		try{
			@SuppressWarnings("unused")
			Estado t = (Estado) ap;
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
