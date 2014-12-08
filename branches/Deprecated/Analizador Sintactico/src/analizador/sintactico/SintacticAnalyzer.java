package analizador.sintactico;

import interfaz.UI;

import java.io.File;

import utils.SystemLog;
import analizador.lexico.LexicAnalyzer;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.sintactico.estructuras.*;
import analizador.lexico.tokens.Error;

/** Clase del Analizador Sintactico
 * @author Santy */
public class SintacticAnalyzer {
	
	/** Analizador Lexico que devuelve los Tokens */
	private LexicAnalyzer la;
	/** Path desde donde se ejecuta el compilador */
	private String syspath;
	/** Nombre del Archivo a Compilar */
	private String filename;
	/** Arbol de Ejecucion que sale */
	private ITree tree;
	/** Log del Sistema */
	private SystemLog log;
	/** Path del Archivo a Compilar */
	private String inpath;
	
	/** Contruye un Analizador Vacio */
	public SintacticAnalyzer(){
		this("");
	}
	
	/** Construye un Analizador inicializando el Path 
	 * @param path - Archivo a analizar */
	public SintacticAnalyzer(String path){
		this.syspath = System.getProperty("user.dir")+"\\";
		this.log = SystemLog.getInstance(this.syspath+"log");
		this.la = new LexicAnalyzer(path);
		this.inpath = this.getPath(path);
		this.filename = this.getFileName(path);
	}
	
	/** Construye un Analizador inicializando con el Archivo
	 * @param f - Archivo a analizar */
	public SintacticAnalyzer(File f){
		this(f.getAbsolutePath());
	}
	
	/** Compila el Archivo pasado por parametro
	 * @param path - Compila el Archivo pasado por parametro
	 * @return - 0 si es exitoso, > 0 si no */
	public int compile(String path){
		this.la = new LexicAnalyzer(path);
		return this.compile();
	}
	
	/** Compila el Archivo inicializado en el contructor! Ojo no usar constructor vacio
	 * @return - 0 si es exitoso, > 0 si no */
	public int compile(){
		if(this.la != null){ //si Lexico no es nulo x no inicializar
			this.tree = new ExecutionTree(this.inpath,this.filename);
			UI.informar("Comienzo del Analisis de: " +this.inpath+this.filename);
			this.log.log_normal("Comienzo del Analisis de: " +this.inpath+this.filename);
			log.log_normal("Comienza el Analizador Lexico");
			if(this.DespRed()){
				UI.informar("--------------------------------------");
				UI.informar("--------------------------------------\n\n");
				UI.informar("Analisis Lexico Correcto");
				UI.informar("Analisis Sintactico Correcto");
				
				this.log.log_normal("--------------------------------------");
				this.log.log_normal("--------------------------------------\n");
				this.log.log_normal("Analisis Lexico Correcto");
				this.log.log_normal("Analisis Sintactico Correcto\n\n");
			}else{
				UI.informar(":(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:");
				UI.informar(":(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:\n\n");
				log.log_error(":(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:");
				log.log_error(":(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:(:\n");
				UI.error("Analisis Sintactico Incorrecto\n\n");
				this.tree.close();
				return 1;
			}
		}else{
			UI.error("Error en el Analizador Lexico","No se inicializo el archivo a declarar");
			return 1;
		}
		this.tree.close();
		return 0;
	}
	
	/** Implementacion del Algoritmo de Desplazamiento Reduccion
	 * @return true si Analisis Sintactio y Lexico Correcto, false sino */
	private boolean DespRed(){
		Token t = this.la.getToken();
		Token tokenAnt = t;
		if(t.getType()!=TokenTypes.EOF){ // si no me llega un EOF o un Error salgo
			if(t.getType()!=TokenTypes.ERROR){
				//ITabla tabla = new Tabla(this.path+"Gramatica/TablaSLR.xls");
				ITabla tabla = new Tabla(this.syspath+"/Gramatica/TablaSLR.xls");
				IPilaLR pila = new PilaLR();
				IGramatica gram = new Gramatica(this.syspath+"/Gramatica/Gramatica.grammar");
			
				Estado cima;
				TableAction act;
				while(true){
					try{
						//System.out.println("Stack: "+pila.toString());
						cima = (Estado)pila.peek();
						act = tabla.findAction(cima.getEstado(), t.getType().toString());
						//System.out.println("Accion: "+act.getType() + " Lex: '" + t.getStringLex()+"' Por: "+cima.getEstado());
						switch(act.getType()){
							case SYNTHETISE: //desplazar
								pila.desplazar(t,act.getPaso());
								//System.out.println("Meto: "+t.getType().toString() + " y " + act.getPaso());
								if(t.getType() != TokenTypes.EOF){
									UI.informarDesplazamieto(t);
									this.tree.insertar(t);
									tokenAnt = t;
									t = this.la.getToken();
								}
								if(t.getType()==TokenTypes.ERROR){
									tratarErroresLexicos(t);
									return false;
								}
								break;
							case REDUCE:
								Produccion p = gram.getProduction(act.getPaso());
								cima = pila.reducir(p);
								UI.informarReduccion(p);
								this.tree.insertar(p);
								//System.out.println("Saco "+ 2*p.getCantDerecha()+" de la pila");
								act = tabla.findGoto(cima.getEstado(), p.getIzquierda());
								if(act.getType()!=TableAction.ActionType.GOTO){
									if(act.getType()==TableAction.ActionType.ERROR){
										this.tratarErroresSintacticos(tokenAnt);
									}else{
										UI.error("Error algoritmico en la tabla", "Un no terminal tiene una reduccion o desplazamiento");
										System.exit(1);
									}
								}
								pila.insertState(act.getPaso());
								//System.out.println("Reduzco por "+ p.getIzquierda() + ". Produccion nro: " + act.getPaso());
								break;
							case ACCEPT:
								return true;
							case ERROR:
								this.tratarErroresSintacticos(tokenAnt);
								return false;
							default:
								UI.error("ERROR","Accion no reconocida!");
							return false;
						}
					}catch(ClassCastException ce){
						UI.error("Error algoritmico", ce.getMessage());
						System.exit(1);
					}
				}
			}else{
				tratarErroresLexicos(t);
				return false;
			}
		}else{
			return true;
		}
	}
	
	/** Trata los Errores Lexicos
	 * @param t Token que dio el Error */
	private void tratarErroresLexicos(Token t){
		Error er;
		while(t.getType() != TokenTypes.EOF){
			if(t.getType() == TokenTypes.ERROR){
				er = (Error)t;
				UI.error(UI.ErrorTypes.LEXICERROR,er.getMensaje(),t);
			}
			t = this.la.getToken();
		}
	}
	
	/** Trata los Errores Sintacticos
	 * @param t - Token que dio el Error */
	private void tratarErroresSintacticos(Token t){
		UI.error(UI.ErrorTypes.SINTACTICERROR,t);
		this.tratarErroresLexicos(t);
	}
	
	/** Obtiene el nombre del Archivo quitandole la extension
	 * @param s - Path con el nombre del archivo
	 * @return El nombre del Archivo sin su extension */
	private String getFileName(String s){
		s.trim();
		int i = s.lastIndexOf('/');
		if(i>=0){
			s = s.substring(i+1,s.length());
		}
		s.trim();
		i = s.lastIndexOf('\\');
		if(i>=0){
			s = s.substring(i+1,s.length());
		}
		s.trim();
		i = s.lastIndexOf('.');
		if(i>=0){
			s = s.substring(0,i);
		}
		return s;
	}
	
	/** Obtiene la carpeta donde esta ubicado un archivo
	 * @param s - Path al Archivo
	 * @return - Path a la carpeta del archivo */
	private String getPath(String s){
		s.trim();
		int i = s.lastIndexOf('/');
		if(i>=0 && i<s.length()){
			s = s.substring(0,i+1);
		}
		s.trim();
		i = s.lastIndexOf('\\');
		if(i>=0 && i<s.length()){
			s = s.substring(0,i+1);
		}
		return s;
	}
}
