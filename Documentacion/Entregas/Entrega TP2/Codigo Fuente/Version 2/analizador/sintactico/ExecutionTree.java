package analizador.sintactico;

import interfaz.UI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import analizador.lexico.tokens.Token;
import analizador.sintactico.estructuras.Produccion;

/** Clase para Hacer el Arbol de Ejecucion 
 * @author Santy */
public class ExecutionTree implements ITree{
	/** Arcuhivo para escribir */
	private Writer writer;
	/** Nivel para mostrar en el archivo */
	private int level;
	
	/** Constructor del Arbol a partir del path y del nombre del archivo para dejarlo en output
	 * @param path - Path donde se va a dejar
	 * @param filename - Nombre que se le da al archivo .tree */
	public ExecutionTree(String path,String filename){
		try {
			String s;
			if(path.contains("/")){
				s = path + "output/";
			}else{
				s = path + "output\\";
			}
			
			if(this.crear_directorio(s)){
				this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(s+filename+".tree"), "utf-8"));
			}else{
				UI.error("Error creando el directorio para el Arbol");
			}
		    
		} catch (Exception ex){
		  UI.error("Error abriendo el archivo del arbol",ex.getMessage());
		}
	}
	
	/** Inserta en el Arbol una hoja Token
	 * @param t - Token a Insertar */
	public void insertar(Token t){
	    try {
	    	String aux = "";
	    	this.level = 0;
	    	aux = aux + t.getType().toString()+".'"+t.getStringLex()+"'\n";
			this.writer.write(aux);
			
		} catch (IOException e) {}
	}
	
	/** Inserta en el Arbol un Nodo Produccion 
	 * @param p - Produccion a Insertar */
	public void insertar(Produccion p){
		try {
			String aux = "";
			this.level++;
	    	for(int i=1;i<=this.level;i++){
	    		aux = aux+"\t";
	    	}
	    	aux = aux + p.toString()+"\n";
			this.writer.write(aux);
		} catch (IOException e) {}
	}
	
	/** Cierra el Archivo del Arbol */
	public void close(){
		try {
			writer.close();
		}catch (Exception ex) {}
	}
	
	/** Si te olvidas de cerrarlo el Garbage collector lo hace */
	protected void finalize(){
		try {
			writer.close();
		}catch (Exception ex) {}
	}
	
	//Crea un directorio segun lo que se paso en el string s
		private boolean crear_directorio(String s){
			File f = new File(s);
			if(f.exists()){
				return true;
			}
			return(f.mkdirs());
		}
}
