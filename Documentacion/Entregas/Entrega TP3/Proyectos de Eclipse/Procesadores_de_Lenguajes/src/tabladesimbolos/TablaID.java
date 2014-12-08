package tabladesimbolos;

import interfaz.UI;

import java.util.HashMap;
import java.util.Iterator;

import tabladesimbolos.declaraciones.Declaraciones;

/** Clase que almacena las declaraciones de funciones o variables
 * @author Santy */
public class TablaID<T extends Declaraciones> {
	private HashMap<String, T> tabla;
	private String contexto;
	
	public TablaID(){
		this.tabla = new HashMap<String,T>();
	}
	
	public TablaID(String context){
		this.tabla = new HashMap<String,T>();
		this.setContexto(context);
	}
	
	public void add(String id, T f){
		this.tabla.put(id, f);
	}
	
	public boolean existeID(String s){
		return this.tabla.containsKey(s);
	}
	
	public T get(String id){
		return this.tabla.get(id);
	}

	public String getContexto() {
		return contexto;
	}

	public void setContexto(String contexto) {
		this.contexto = contexto;
	}
	
	public void printTable(){
		Iterator<T> it = this.tabla.values().iterator();
		UI.informar("Imprimiendo Tabla "+this.contexto);
		while(it.hasNext()){
			System.out.println(it.next());
		}
		UI.informar("---------------------------------------------------------");
	}
}
