package analizador.sintactico;

import interfaz.UI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/** Clase para Hacer el Arbol de Ejecucion 
 * @author Santy */
public class ExecutionTree implements ITree{
	/** Arcuhivo para escribir */
	private Writer writer;
	private Writer codeWriter;
	/** Nivel para mostrar en el archivo */
	private int level;
	private int codeLevel;
	
	private static ExecutionTree instance;
	
	
	public static ExecutionTree getInstance(String path,String filename){
		if(instance == null){
			instance = new ExecutionTree(path,filename);
		}
		return instance;
	}
	
	public static ExecutionTree getInstance(){
		return instance;
	}
	
	/** Constructor del Arbol a partir del path y del nombre del archivo para dejarlo en output
	 * @param path - Path donde se va a dejar
	 * @param filename - Nombre que se le da al archivo .tree */
	private ExecutionTree(String path,String filename){
		try {
			String s;
			if(path.contains("/")){
				s = path + "output/";
			}else{
				s = path + "output\\";
			}
			
			if(this.crear_directorio(s)){
				this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(s+filename+".tree"), "utf-8"));
				this.codeWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(s+filename+".asm"), "utf-8"));
			}else{
				UI.error("Error creando el directorio para el Arbol");
			}
			this.generarCOM();
		    
		}catch(IOException ioe){
			UI.error("Error creando el esqueleto del archivo COM",ioe.getMessage());
		}catch (Exception ex){
		  UI.error("Error creando la salida",ex.getMessage());
		}
	}
	
	/** Inserta en el Arbol una hoja Token
	 * @param t - Token a Insertar */
	public void ident(){
		this.level++;
	}
	
	/** Inserta en el Arbol una hoja Token
	 * @param t - Token a Insertar */
	public void deident(){
		if(this.level>0){
			this.level--;
		}
	}
	
	/** Inserta en el Arbol una hoja Token
	 * @param t - Token a Insertar */
	public void codeIdent(){
		this.codeLevel++;
	}
	
	/** Inserta en el Arbol una hoja Token
	 * @param t - Token a Insertar */
	public void codeDeident(){
		if(this.codeLevel>0){
			this.codeLevel--;
		}
	}
	
	/** Inserta en el Arbol un Nodo Produccion 
	 * @param p - Produccion a Insertar */
	public void insertar(String s){
		try {
			String aux = "";
	    	for(int i=1;i<=this.level;i++){
	    		aux = aux+"\t";
	    	}
	    	aux = aux + s+"\n";
			this.writer.write(aux);
			this.writer.flush();
		} catch (IOException e) {}
	}
	
	/** Inserta en el Arbol un Nodo Produccion 
	 * @param p - Produccion a Insertar */
	public void insertCode(String s){
		try {
			String aux = "";
			for(int i=1;i<=this.codeLevel;i++){
	    		aux = aux+"\t";
	    	}
	    	aux = aux + s+"\n";
			this.codeWriter.write(aux);
			this.codeWriter.flush();
		} catch (IOException e) {}
	}
	
	/** Cierra el Archivo del Arbol */
	public void close(){
		try {
			writer.close();
			this.codeWriter.close();
		}catch (Exception ex) {}
	}
	
	/** Si te olvidas de cerrarlo el Garbage collector lo hace */
	protected void finalize(){
		try {
			writer.close();
			this.codeWriter.close();
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
	
	private void generarCOM() throws IOException{
		
		//Genero el encabezado del .asm sacandolo del ASMHeader.asm
		String path = System.getProperty("user.dir");
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path+"/res/ASMHeader.asm")));		
		try {		    
		    String s = "";
		    while ((s = bufferedReader.readLine()) != null) {
		        this.codeWriter.write(s);
		        this.codeWriter.write("\n");
		        this.codeWriter.flush();
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}		
	}
}
