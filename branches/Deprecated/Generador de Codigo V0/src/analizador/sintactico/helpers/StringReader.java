package analizador.sintactico.helpers;

import interfaz.UI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import exceptions.StringReaderException;

/**Clase para leer de un archivo de texto plano de n tamanio
 * @author Santy */
public class StringReader {
	/** Buffer de Lectura usado */
	private BufferedReader br;
	
	/** Inicializacion a traves del path
	 * @param path String al Archivo
	 * @throws StringReaderException en caso que no pueda abrir */
	public StringReader(String path) throws StringReaderException{
		try{
			if(this.crear_directorio(path)){
				this.br = new BufferedReader(new FileReader(path));
			}else{
				UI.error("Error creando el directorio del Arbol");
			}
		}catch(Exception e){
			UI.error("Error Abriendo el Archivo: " + path,e.getMessage());
			throw new StringReaderException(e.getMessage(),e);
		}
	}
	
	/** Inicializacion a traves de un File
	 * @param f el arhivo pasado por parametro
	 * @throws StringReaderException en caso que no pueda abrir */
	public StringReader(File f) throws StringReaderException{
		try{
			this.br = new BufferedReader(new FileReader(f));
		}catch(Exception e){
			UI.error("Error Abriendo el Archivo: " + f.getPath(),e.getMessage());
			throw new StringReaderException(e.getMessage(),e);
		}
	}
	
	/** Lee la siguiente linea o null cuando termina
	 * @return la linea hasta el /n o null
	 * @throws StringReaderException en caso de no poder leer*/
	public String readline() throws StringReaderException{
		try {
	        return this.br.readLine();
		}catch(Exception e){
			UI.error("Error leyendo una linea",e.getMessage());
			throw new StringReaderException(e.getMessage(),e);
		}
	}
	
	/** Cierra el archivo de la gramatica */
	public void closeFile(){
		try{
			br.close();
		}catch(Exception e){
			UI.error("Error cerrando el archivo de la gramatica",e.getMessage());
		}
	}
	
	/** Por si me olvido de cerrarlo, lo cierra */
	protected void finalize(){
		try{
			br.close();
		}catch(Exception e){
			
		}
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