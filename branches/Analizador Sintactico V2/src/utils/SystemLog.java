package utils;

import interfaz.UI;

import java.io.IOException;
import java.io.File;
import java.util.logging.*;

/** Clase que facilita el armado de un log usando logger
 * @author Santiago Russo */
public class SystemLog{
	public static SystemLog instance;
	private Logger logger;
	private FileHandler fh;
	
	/** Crea el log en el directorio log\
	 * @param s String -  Nombre del archivo .log. */
	public SystemLog(String s){
		logger = Logger.getLogger(s);
		if(this.crear_directorio(s)){ //si no existe el directiorio pasado lo creo
			try {
				if(s.contains("/")){
					if(s.charAt(s.length()-1)!='/'){
						s = s+"/";
					}
					s = s+"LOG.log";
				}else{
					if(s.charAt(s.length()-1)!='\\'){
						s = s+"\\";
					}
					s = s+"LOG.log";
				}
				fh = new FileHandler(s,200000,8,true);
				logger.addHandler(fh);
				logger.setLevel(Level.ALL);
				logger.setUseParentHandlers(false);
				SimpleFormatter formatter = new SimpleFormatter();
				fh.setFormatter(formatter);
			}catch (SecurityException e) {
				UI.error(e.getMessage());
			} catch (IOException e) {
				UI.error(e.getMessage());
			}
		}else{
			UI.error("Error creando el directorio para el log: "+s);
		}
	}
	
	public static SystemLog getInstance(String path){
		if(instance == null){
			instance = new SystemLog(path);
		}
		return instance;
	}
	
	public static SystemLog getInstance(){
		if(instance == null){
			instance = new SystemLog(System.getProperty("user.dir")+"\\log\\");
		}
		return instance;
	}
	
	/** Inserta en el log un error
	 * @Definition public void log_error(String s)
	 * @param s String -  Mensaje a guardar en el log.
	 */
	public void log_error(String s){
		logger.log(Level.SEVERE,s);
	}
	
	/** Inserta en el log un error
	 * @Definition public void log_error(String s)
	 * @param s String -  Mensaje a guardar en el log.
	 */
	public void log_error(String title, String s){
		logger.log(Level.SEVERE,title + ": " + s);
	}
	
	/** Inserta en el log un warning
	 * @Definition public void log_warning(String s)
	 * @param s String -  Mensaje a guardar en el log.
	 */
	public void log_warning(String s){
		logger.log(Level.WARNING,s);
	}
	/** Inserta en el log un mensaje normal
	 * @Definition public void log_normal(String s)
	 * @param s String -  Mensaje a guardar en el log.
	 */
	public void log_normal(String s){
		logger.log(Level.FINE,s);
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