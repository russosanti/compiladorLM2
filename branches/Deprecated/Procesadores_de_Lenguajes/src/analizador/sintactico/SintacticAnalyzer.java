package analizador.sintactico;

import interfaz.UI;

import java.io.File;

import exceptions.AlgorithmicError;
import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import tabladesimbolos.iTablaDeSimbolos;
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
	
	private Token tokenAnt;
	private Token t;
	IPilaLR pila;
	
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
				UI.informar("Analisis Correcto");
				
				this.log.log_normal("--------------------------------------");
				this.log.log_normal("--------------------------------------\n");
				this.log.log_normal("Analisis Correcto");
			}else{
				UI.informar(":( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :(");
				UI.informar(":( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :(\n\n");
				log.log_error(":( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :(");
				log.log_error(":( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :( :(\n");
				UI.error("Analisis Incorrecto\n\n");
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
		boolean setRecu = false;
		boolean pasoParentesis = false;
		boolean accept = true;
		this.t = this.la.getToken();
		if(t.getType()!=TokenTypes.EOF){ // si no me llega un EOF o un Error salgo
			if(t.getType()!=TokenTypes.ERROR){
				//ITabla tabla = new Tabla(this.path+"Gramatica/TablaSLR.xls");
				ITabla tabla = new Tabla(this.syspath+"/Gramatica/TablaSLR.xls");
				this.pila = new PilaLR();
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
									//XXX eso lo hago para cambiar el contexto de la tabla
									if(this.tokenAnt != null && ((this.tokenAnt.getType()==TokenTypes.FUNCTION || this.tokenAnt.getType()==TokenTypes.PROCEDURE) && this.t.getType() == TokenTypes.ID)){
										iTablaDeSimbolos tablaSimbolo = SingleTabla.getInstance();
										tablaSimbolo.setContexto(t.getStringLex());
										tablaSimbolo.setIdRecur(t.getStringLex());
										tablaSimbolo.setFuncRecur(true);
										setRecu = true;
									}
									if(setRecu && this.tokenAnt.getType()==TokenTypes.PARENTESIS_CIERRE && t.getType() == TokenTypes.DEF_TIPO){ //para setear el tipo
										pasoParentesis = true;
										setRecu = false;
									}
									if(pasoParentesis && this.tokenAnt.getType() == TokenTypes.DEF_TIPO && this.t.getType()==TokenTypes.TIPO){
										pasoParentesis = false;
										iTablaDeSimbolos tablaSimbolo = SingleTabla.getInstance();
										tablaSimbolo.setRetornoRecur(Tipo.parseTipo(t.getStringLex()));
									}
									if(t.getType()==TokenTypes.END_FUNC || t.getType()==TokenTypes.END_PROC){
										pasoParentesis = false;
										setRecu = false;
									}
									this.tokenAnt = t;
									t = this.la.getToken();
								}
								if(t.getType()==TokenTypes.ERROR){
									tratarErroresLexicos(t);
									return false;
								}
								break;
							case REDUCE:
								Produccion p = gram.getProduction(act.getPaso());
								UI.informarReduccion(p);
								try{
									cima = pila.reducir(p);
								}catch(AlgorithmicError err){
									//TODO comentar lo de abajo al entregar
									UI.error("Error causado por error Sintactico", err.toString());
								}catch(Exception e){
									this.tratarErroresSemanticos(t,e.toString());
									cima = this.pila.estadoAnt();
									accept = false;
								}
								this.tree.insertar(p);
								//System.out.println("Saco "+ 2*p.getCantDerecha()+" de la pila");
								act = tabla.findGoto(cima.getEstado(), p.getIzquierda());
								if(act.getType()!=TableAction.ActionType.GOTO){
									if(act.getType()==TableAction.ActionType.ERROR){
										this.tratarErroresSintacticos(this.tokenAnt);
										accept = false;
										
									}else{
										UI.error("Error algoritmico en la tabla", "Un no terminal tiene una reduccion o desplazamiento");
										System.exit(1);
									}
								}
								pila.insertState(act.getPaso());
								//System.out.println("Reduzco por "+ p.getIzquierda() + ". Produccion nro: " + act.getPaso());
								break;
							case ACCEPT:
								return accept;
							case ERROR:
								this.tratarErroresSintacticos(this.tokenAnt);
								accept = false;
								if(this.t.getType() == TokenTypes.EOF){
									return false;
								}
								break;
								//return false;
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
		UI.informar("Salida del Compilador por Analisis de Errores Lexicos");
	}
	
	/** Trata los Errores Sintacticos
	 * @param t - Token que dio el Error */
	private void tratarErroresSintacticos(Token t){
		UI.error(UI.ErrorTypes.SINTACTICERROR,t);
		this.pila.errorMode();
		while(this.t.getType() != TokenTypes.EOF && this.t.getType()!=TokenTypes.ENDLINE){
			if(this.t.getType()==TokenTypes.ERROR){
				this.tratarErroresLexicos(t);
			}
			this.t = this.la.getToken();
		}
		this.tokenAnt = this.t;
		this.t = this.la.getToken();
	}
	
	/** Trata los Errores Semanticos
	 * @param */
	private void tratarErroresSemanticos(Token t, String error){
		UI.error("Error Semantico en fila " + t.getFila() + " columna " + t.getColumna(), error);
		//System.exit(1);
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
