package analizador.sintactico.estructuras;

import interfaz.UI;
import java.util.ArrayList;
import java.util.Iterator;
import analizador.sintactico.helpers.StringReader;

/** Clase que contiene la gramatica para el Algoritmo de Desplazamiento/Reduccion
 * @author Santy */
public class Gramatica implements IGramatica{
	 /** Contenedor de todas las gramaticas ordenadas por indice */
	public ArrayList<Produccion> gram;
	
	/** Inicializa la gramatica del path pasado
	 * @param path direccion del archivo*/
	public Gramatica(String path){
		gram = new ArrayList<Produccion>();
		carga(path);
		//deberia de tirar excepcion si no logra cargar la gramatica
	}
	
	/** Devuelve la produccion numero num
	 * @param num Numero de la produccion a devolver
	 * @return La produccion numero num o null si no la encuentra */
	public Produccion getProduction(int num){
		if(num>=this.gram.size()){
			return null;
		}
		return this.gram.get(num);
	}
	
	/** Realiza la carga de la gramatica
	 * @param path Direccion de la gramatica
	 * @return true si logra cargarlo, false sino*/
	private boolean carga(String path){
		try{
			StringReader re = new StringReader(path);
			String aux = re.readline();
			while(aux != null){
				this.gram.add(tratarProduccion(aux));
				aux = re.readline();
			}
			re.closeFile();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/** Trata cada linea de la gramatica y crea la Produccion correspondiente
	 * @param aux La linea de la gramatica A -> <B> b <C> c
	 * @return Produccion */
	private Produccion tratarProduccion(String aux){
		aux = aux.trim();
		aux = aux.toUpperCase();
		int i = aux.indexOf("->");
		String izq = aux.substring(0, i);
		aux = aux.substring(i+2,aux.length());
		ArrayList<String> arr = new ArrayList<String>();
		boolean lambda = false;
		if(aux.length()>0){
			izq = izq.trim();
			aux = aux.trim();
			if(aux.equalsIgnoreCase("λ")){
				lambda = true;
			}
			i = aux.indexOf(" ");
			String der;
			while(i>0){
				der = aux.substring(0,i);
				der = der.trim();
				aux = aux.substring(i+1,aux.length());
				aux = aux.trim();
				arr.add(der);
				i = aux.indexOf(" ");
			}
			if(aux!=null){
				aux = aux.trim();
				if(aux.length()>0){
					arr.add(aux);
				}
			}
		}else{
			UI.error("Error cargando la gramatica","Falta la produccion de la derecha");
		}
		return new Produccion(izq,arr,lambda);
	}
	
	/** Muestra la gramatica cargada */
	public void showGrammar(){
		Iterator<Produccion> it = this.gram.iterator();
		Produccion aux;
		int i = 0;
		while(it.hasNext()){
			aux = it.next();
			UI.informar(i + ") " + aux.toString());
			i++;
		}
	}
}
