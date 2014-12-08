package analizador.sintactico.estructuras;

import java.util.ArrayList;
import java.util.Iterator;

/** Manejador de las producciones
 * @author Santy */
public class Produccion {
	/** Representa el no terminal a la izquierda de una produccion */
	private String izquierda;
	/** Representa la cantidad de terminales y no terminales a la derecha de la produccion */
	private ArrayList<String> derecha;
	/**Representa una produccion de lambda para no leer*/
	private boolean lambda;
	
	/** Crea la produccion
	 * @param i el no terminal de la izq
	 * @param d la cantidad de la derecha*/
	public Produccion(String i,ArrayList<String> der,boolean l){
		this.izquierda = i;
		this.derecha = der;
		this.lambda = l;
	}
	
	/** @return Devuelve el no terminal a la izq */
	public String getIzquierda() {
		return izquierda;
	}
	 /** @return Devuelve la cantidad de la derecha */
	public int getCantDerecha() {
		if(this.lambda){
			return 0;
		}
		return derecha.size();
	}
	
	 /** @return Devuelve la cantidad de la derecha */
	public ArrayList<String> getDerecha() {
		if(derecha==null){
			this.derecha = new ArrayList<String>();
			return this.derecha;
		}
		return derecha;
	}
	
	/** Se fija si la produccion es una produccion Lambda
	 * @return - True si genera solo Lambda, sino False */
	public boolean isLambda(){
		return this.lambda;
	}
	
	public String toString(){
		String aux = this.izquierda + " ->";
		Iterator<String> it = this.derecha.iterator();
		while(it.hasNext()){
			aux = aux + " " + it.next();
		}
		return aux;
	}
}
