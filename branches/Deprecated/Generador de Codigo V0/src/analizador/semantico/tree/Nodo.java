package analizador.semantico.tree;

import java.util.ArrayList;
import java.util.Iterator;

import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;
import exceptions.CodeException;
import exceptions.TreeException;

public class Nodo<T extends INodo> implements INodo{
	protected String id;
	protected TipoNodo tipoNodo;
	protected ArrayList<T> hijos;
	
	public Nodo(){
		this.hijos = new ArrayList<T>();
	}
	
	public Nodo(String s){
		this.id = s;
		this.hijos = new ArrayList<T>();
	}
	
	public Nodo(TipoNodo t){
		this.tipoNodo = t;
	}
	
	public Nodo(ArrayList<T> hi){
		this.hijos = hi;
	}
	
	
	public Nodo(String s, TipoNodo t, ArrayList<T> hi){
		this.id = s;
		this.tipoNodo = t;
		this.hijos = hi;
	}
	
	
	public Nodo(TipoNodo t, ArrayList<T> hi){
		this.tipoNodo = t;
		this.hijos = hi;
	}
	
	
	public Nodo(String s, ArrayList<T> hi){
		this.id = s;
		this.hijos = hi;
	}
	
	public Nodo(T h1){
		this.hijos = new ArrayList<T>();
		this.hijos.add(h1);
	}
	
	public Nodo(String s, TipoNodo t, T h1){
		this.hijos = new ArrayList<T>();
		this.tipoNodo = t;
		this.id = s;
		this.hijos.add(h1);
	}
	
	public Nodo(TipoNodo t, T h1){
		this.hijos = new ArrayList<T>();
		this.tipoNodo = t;
		this.hijos.add(h1);
	}
	
	public Nodo(String s, T h1){
		this.id = s;
		this.hijos = new ArrayList<T>();
		this.hijos.add(h1);
	}
	
	public Nodo(T h1, T h2){
		this.hijos = new ArrayList<T>();
		this.hijos.add(h1);
		this.hijos.add(h2);
	}
	
	public Nodo(String s, TipoNodo t, T h1, T h2){
		this.hijos = new ArrayList<T>();
		this.tipoNodo = t;
		this.id = s;
		this.hijos.add(h1);
		this.hijos.add(h2);
	}
	
	public Nodo(TipoNodo t, T h1, T h2){
		this.hijos = new ArrayList<T>();
		this.tipoNodo = t;
		this.hijos.add(h1);
		this.hijos.add(h2);
	}
	
	public Nodo(String s, T h1, T h2){
		this.hijos = new ArrayList<T>();
		this.id = s;
		this.hijos.add(h1);
		this.hijos.add(h2);
	}
	
	public Nodo(T h1, T h2, T h3){
		this.hijos = new ArrayList<T>();
		this.hijos.add(h1);
		this.hijos.add(h2);
		this.hijos.add(h3);
	}
	
	public Nodo(String s, TipoNodo t, T h1, T h2, T h3){
		this.hijos = new ArrayList<T>();
		this.tipoNodo = t;
		this.id = s;
		this.hijos.add(h1);
		this.hijos.add(h2);
		this.hijos.add(h3);
	}
	
	public Nodo(TipoNodo t, T h1, T h2, T h3){
		this.hijos = new ArrayList<T>();
		this.tipoNodo = t;
		this.hijos.add(h1);
		this.hijos.add(h2);
		this.hijos.add(h3);
	}
	
	public Nodo(String s, T h1, T h2, T h3){
		this.hijos = new ArrayList<T>();
		this.id = s;
		this.hijos.add(h1);
		this.hijos.add(h2);
		this.hijos.add(h3);
	}
	
	public Nodo(T h1, T h2, T h3, T h4){
		this.hijos = new ArrayList<T>();
		this.hijos.add(h1);
		this.hijos.add(h2);
		this.hijos.add(h3);
		this.hijos.add(h4);
	}
	
	public Nodo(String s, TipoNodo t, T h1, T h2, T h3, T h4){
		this.hijos = new ArrayList<T>();
		this.tipoNodo = t;
		this.id = s;
		this.hijos.add(h1);
		this.hijos.add(h2);
		this.hijos.add(h3);
		this.hijos.add(h4);
	}
	
	public Nodo(TipoNodo t, T h1, T h2, T h3, T h4){
		this.hijos = new ArrayList<T>();
		this.tipoNodo = t;
		this.hijos.add(h1);
		this.hijos.add(h2);
		this.hijos.add(h3);
		this.hijos.add(h4);
	}
	
	public Nodo(String s, T h1, T h2, T h3, T h4){
		this.hijos = new ArrayList<T>();
		this.id = s;
		this.hijos.add(h1);
		this.hijos.add(h2);
		this.hijos.add(h3);
		this.hijos.add(h4);
	}
	
	public Nodo(T h1, T h2, T h3, T h4, T h5){
		this.hijos = new ArrayList<T>();
		this.hijos.add(h1);
		this.hijos.add(h2);
		this.hijos.add(h3);
		this.hijos.add(h4);
		this.hijos.add(h5);
	}
	
	public Nodo(String s, TipoNodo t, T h1, T h2, T h3, T h4, T h5){
		this.hijos = new ArrayList<T>();
		this.tipoNodo = t;
		this.id = s;
		this.hijos.add(h1);
		this.hijos.add(h2);
		this.hijos.add(h3);
		this.hijos.add(h4);
		this.hijos.add(h5);
	}
	
	public Nodo(TipoNodo t, T h1, T h2, T h3, T h4, T h5){
		this.hijos = new ArrayList<T>();
		this.tipoNodo = t;
		this.hijos.add(h1);
		this.hijos.add(h2);
		this.hijos.add(h3);
		this.hijos.add(h4);
		this.hijos.add(h5);
	}
	
	public Nodo(String s, T h1, T h2, T h3, T h4, T h5){
		this.id = s;
		this.hijos = new ArrayList<T>();
		this.hijos.add(h1);
		this.hijos.add(h2);
		this.hijos.add(h3);
		this.hijos.add(h4);
		this.hijos.add(h5);
	}
	
	public void addHijo(T hi){
		this.hijos.add(hi);
	}
	
	public void addHijos(ArrayList<T> hi){
		Iterator<T> iterator = hi.iterator();
		T aux;
		while(iterator.hasNext()){
			aux = iterator.next();
			this.hijos.add(aux);
		}
	}
	
	public T getHijo(int pos) throws TreeException{
		try{
			return this.hijos.get(pos);
		}catch(Exception e){
			throw new TreeException("No se pudo devolver el Hijo " + pos + "del Arbol. " + e.getMessage());
		}
	}
	
	public ArrayList<T> getHijos(){
		return this.hijos;
	}
	
	public Iterator<T> iterator(){
		return this.hijos.iterator();
	}
	
	public TipoNodo getTipoNodo(){
		return this.tipoNodo;
	}
	
	public void addFirst(T elem){
		this.hijos.add(0, elem);
	}
	
	public void addFirst(ArrayList<T> elements){
		this.hijos.addAll(0, elements);
	}
	
	public void addFirst(Nodo<T> elements){
		this.hijos.addAll(0, elements.getHijos());
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			Iterator<T> it = this.hijos.iterator();
			prin.ident();
			while(it.hasNext()){
				it.next().printTree();
			}
			prin.deident();
		}catch(Exception e){
			
		}
	}
	
	public String toString(){
		if(this.id == null){
			return "Tipo Nodo: " +this.tipoNodo;
		}
		return "Nombre: " + this.id + " Tipo Nodo: " +this.tipoNodo;
	}

	
	public void generateCode() throws CodeException {
		Iterator<T> it = this.hijos.iterator();
		while(it.hasNext()){
			it.next().generateCode();
		}
	}
}
