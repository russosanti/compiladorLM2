package analizador.sintactico.estructuras;

import analizador.lexico.tokens.Token;

/** Interfaz para el manejador de la pila
 * @author Santy
 * @param <T> Tipo que se guarda en la pila*/
public interface IPilaLR{
	
	/**Inserta en la pila a y dps s
	 * @param a el no terminal que lee de la entrada
	 * @param s el estado del automata
	 * @return true si no hay error, false si lo hay*/
	public boolean desplazar(Token a,Estado s);
	
	/**Inserta en la pila a y dps s
	 * @param a el no terminal que lee de la entrada
	 * @param s el estado del automata
	 * @return true si no hay error, false si lo hay*/
	public boolean desplazar(Token a,int s);
	
	/**Saca 2*b elementos de la pila e inserta A
	 * @param A Cantidad de elementos a insertar
	 * @param B cantidad de elementos que se sacan
	 * @return true si pudo redurir sino false */
	public Estado reducir(NoTerminal A, int b);
	
	/**Saca 2*b elementos de la pila e inserta A
	 * @param A Cantidad de elementos a insertar
	 * @param B cantidad de elementos que se sacan
	 * @return true si pudo redurir sino false */
	public Estado reducir(String A, int b);
	
	/**Saca 2*b elementos de la pila e inserta A
	 * @param A Cantidad de elementos a insertar
	 * @param B cantidad de elementos que se sacan
	 * @return true si pudo redurir sino false */
	public Estado reducir(Produccion p);
	
	/** Inserta el estado s en la pila luego de reducir
	 * @param s estado a insertar
	 * @return true si puede, false si falla*/
	public boolean insertState(Estado s);
	
	/** Inserta el estado s en la pila luego de reducir
	 * @param s estado a insertar
	 * @return true si puede, false si falla*/
	public boolean insertState(int s);
	
	/** Se fija y devuelve el Estado en el tope de la pila sin sacarlo
	 * @return Estado en el tope de la pila */
	public LRApilable peek();
}