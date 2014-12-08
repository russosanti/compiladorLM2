package tabladesimbolos;

import java.util.HashMap;
import java.util.Iterator;
import analizador.lexico.tokens.ID;

/** Clase que almacena las declaraciones de funciones o variables
 * @author Santy */
public class TablaID {
	private HashMap<String,ID> tabla;
	
	public TablaID(){
		this.tabla = new HashMap<String,ID>();
	}
	
	/**Guardo el Token de la primera aparicion del identificador
	 * @param p
	 * @return - true si lo guardo false si ya existe */
	public boolean add(ID p){
		if(!this.existeID(p.getLexema())){
			this.tabla.put(p.getLexema(),p);
			return true;
		}
		return false;
	}
	
	public boolean existeID(String s){
		return this.tabla.containsKey(s);
	}
	
	public Iterator<String> getKeyIterator(){
		return this.tabla.keySet().iterator();
	}
}
