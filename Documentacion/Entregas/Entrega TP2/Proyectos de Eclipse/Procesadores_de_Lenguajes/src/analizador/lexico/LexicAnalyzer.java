package analizador.lexico;

import interfaz.UI;

import java.io.File;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.TablaDeSimbolos;
import utils.SystemLog;
import analizador.lexico.tokens.*;
import analizador.lexico.tokens.Error;

/**
 * 	Analizador Lexico, devuelve los tokens del archivo que se le pasa en su constructor 
 *
 */
public class LexicAnalyzer implements ILexicAnalyzer{
	
	public File file; 
	private FileReader fr; 
	private Coordenadas coord = new Coordenadas(1,1);
	private StringBuffer lexema = new StringBuffer(""); 
	private static final int EOF = -1;
	private Token last_token; //guardo el ultimo char excepto si es ignorable
	public SystemLog log;
	
	/** 
	 * El Analizador Lexico se encarga de devolver los tokens del archivo que recive como parametro 
	 * 
	 * @param f	Archivo con el codigo a ser compilado 
	 */
	public LexicAnalyzer(File f){
		file =f;
		try {
			fr = new FileReader(file);
			log = SystemLog.getInstance(f.getName());
			log.log_normal("Inicio de la Compilacion...");
		} catch (Exception e) {
			UI.error("Archivo inexistente",e.getMessage());
			System.exit(1);
		}
	} 
	
	public LexicAnalyzer(String path){
		try{
			this.file = new File(path);
			fr = new FileReader(file);
			log = SystemLog.getInstance();
			log.log_normal("Inicio de la Compilacion...");
		}catch (Exception e){
			UI.error("Archivo inexistente",e.getMessage());
			System.exit(1);
		}
	} 
	/**   
	 * Esta funcion devuelve el siguiente token del archivo 
	 */
	public Token getToken(){
		Caracter caracter = new Caracter();
		Token t = null;
		this.lexema.setLength(0);
		
		caracter.setSimbolo(fr.read());
		tratarIgnorables(caracter);
		
		switch (caracter.getTipo()){ 
			case NUMERO:
				//Tratar Numero
				t = this.tratarNumero(caracter);
				break;
			case CADENA: 
				//Tratar Cadena 
				t = this.tratarCadena(caracter);
				break; 
			case SYMBOLO: 
				//Tratar Operador
				t = this.tratarOperador(caracter); 
				break;
			case PARENTESIS_APERTURA:
				//Trato (
				try{
					t = new Symbolo(this.coord.getY(),this.coord.getX(),"(", TokenTypes.PARENTESIS_APERTURA);
				}catch(Exception e){
					UI.error("Error al tratar el parentesis de apertura", e.getMessage());
				}
				break;
			case PARENTESIS_CIERRE:
				//Trato )
				try{
					t = new Symbolo(this.coord.getY(),this.coord.getX(),")",TokenTypes.PARENTESIS_CIERRE);
				}catch(Exception e){
					UI.error("Error al tratar el parentesis de cierre", e.getMessage());
				}
				break;
			case ENDLINE:
				//Tratar ;
				try{
					t = new Symbolo(this.coord.getY(),this.coord.getX(),";",TokenTypes.ENDLINE);
				}catch(Exception e){
					UI.error("Error al tratar el ';'", e.getMessage());
				}
				break;
			case CORCHETE_APERTURA:
				//Trato [
				try{
					t = new Symbolo(this.coord.getY(),this.coord.getX(),"[",TokenTypes.CORCHETE_APERTURA);
				}catch(Exception e){
					UI.error("Error al tratar el Corchete de Apertura", e.getMessage());
				}
				break;
			case CORCHETE_CIERRE:
				//Trato ]
				try{
					t = new Symbolo(this.coord.getY(),this.coord.getX(),"]",TokenTypes.CORCHETE_CIERRE);
				}catch(Exception e){
					UI.error("Error al tratar el Corchete de Cierre", e.getMessage());
				}
				break;
			case ID: 
				//Tratar Palabra
				t = this.tratarPalabra(caracter);
				break;
			case EOF:  
				//Tratar EOF 
				t = new Eof(coord.getY(),coord.getX());
				break; 
			case ERROR:
				//Trato el error
				t = this.tratarError(caracter);
				break;
			default: 
				//Tratar error por caracter invalido detectado
				t = this.tratarError(caracter);
				break;
		}
		
		if(t.getType() == TokenTypes.ERROR){
			this.log.log_warning(t.toString());
		}else{
			if(t.getType() == TokenTypes.EOF){
				this.log.log_normal("Finalizado el analisis Lexico del archivo");
				this.log.log_normal("---------------------------------------------------------------------------");
				this.log.log_normal("");
			}
		}
		
		this.last_token = t;
		return t;
	} 
	
	//Remuevo espacios, comentarios, tabs y enters del principio del lexema
	private void tratarIgnorables(Caracter caracter){
		while(caracter.esIgnorable()){
			if(caracter.getSimbolo() == '\n'){
            	coord.setY(coord.getY() + 1);
            	coord.setX(1);
            	caracter.setSimbolo(fr.read());
			}else{ 
				if(caracter.esPpioComentario()){
					this.tratarComentario(caracter);
				}else{ 
					if(caracter.getSimbolo()=='\t'){    
						coord.setX(coord.getX() + (4 - ((coord.getX()-1 )%4)));
						caracter.setSimbolo(fr.read());
					}else{	
						coord.setX(coord.getX()+1);
						caracter.setSimbolo(fr.read());
					}
				}
			}
		}
	}
	
	/** 
	 * Ignora los comentarios entre llaves, 
	 * 
	 * @param coord Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	Objeto Caracter con el primer simbolo a tratar  
	 */
	public void tratarComentario(Caracter c) 
	{
		while (fr.peek()!=EOF && c.getSimbolo()!='}'){
			if (c.getSimbolo() == '\n'){
            	coord.setY(coord.getY() + 1);
            	coord.setX(1);
			}
			c.setSimbolo(fr.read());
			coord.setX(coord.getX()+1);
		}
		if ( fr.peek() != -1)
		{
			c.setSimbolo(fr.read());
			coord.setX(coord.getX()+1);
		}	
	}

	
	/**
	 * Devuelve un token de Entero, Natural o Error 
	 * @param coord Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	Objeto Caracter con el primer simbolo a tratar
	 * 
	 * @return 		Un token de Numero que puede ser Entero o Natural 
	 */
	public Token tratarNumero(Caracter c) 
	{
		lexema.setLength(0);
		int colmini=coord.getX();

		lexema.append((char)c.getSimbolo());
		while ((fr.peek()!=-1 && !esCortePalabraONumero( (fr.peek()) )) && Character.isDigit(fr.peek())){
			leerYAcumular(coord,fr,c,lexema);
		}
		coord.setX(coord.getX()+1);
		if(!esCortePalabraONumero((fr.peek()))){
			while ((fr.peek()!=-1 && !esCortePalabraONumero((fr.peek())))){
				leerYAcumular(coord,fr,c,lexema);
			}
			return new Error(coord.getY(), colmini, lexema.toString(), 1);
		}else{
			Entero entero = new Entero(coord.getY(), colmini, Integer.parseInt(lexema.toString()));  	
			return entero;
		}
	}

	/** 
	 * Trata Cadena de caracteres y devuelve un token de tipo Cadena o Error si la cadena no cierra   
	 * 
	 * @param coord Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	Objeto Caracter con el primer simbolo a tratar
	 * 
	 * @return 		Token Cadena con el string entre comillas simples */
	public Token tratarCadena(Caracter c) {
		lexema.setLength(0);
		int colmini=coord.getX();
		
		lexema.append((char)c.getSimbolo());
		while ( fr.peek()!=-1 && fr.peek()!='\''){ //permito meter \n en la cadena
			leerYAcumularCadena(coord,fr,c,lexema);
		}
		
		coord.setX(coord.getX()+1);
		if (fr.peek()=='\''){ //si no termina como debe!
			leerYAcumularCadena(coord,fr,c,lexema);
			Cadena cadena = new Cadena(coord.getY(), colmini, lexema.toString());
			return	cadena; 
		}else{   
			//f.peek()!=-1
			Error tokenError = new Error(coord.getY(), colmini, lexema.toString(), 2); 
			return tokenError;
		}	
	}	

	/**
	 * Devuelve un token de Operador o Error si se trata de un operador no valido  
	 * 
	 * @param coord Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	Objeto Caracter con el primer simbolo a tratar
	 * 
	 * @return 		Un token de Operador  
	 */
	private Token tratarOperador(Caracter c){  //aca hay que verificar que si el caracter anterior era un igual y es
		lexema.setLength(0); // un - hay que tratarlo como entero negativo!!
		int colmini=coord.getX();
		boolean error=false;
		lexema.append((char)c.getSimbolo());
		switch(c.getSimbolo()){
			case '-':
				if((this.last_token!=null) && ((this.last_token.getType() == TokenTypes.SYMBOLO) || (this.last_token.getType() == TokenTypes.PARENTESIS_APERTURA) || (this.last_token.getType() == TokenTypes.ASIGNACION))){
					Symbolo op = (Symbolo)this.last_token;
					if((op.getLexema().equals("=") || op.getLexema().equals(":=") || (op.getLexema().equals("("))) && Character.isDigit(fr.peek())){
						return this.tratarNumero(c);
					}
				}
				break;
		
			case ':':
				if(fr.peek()=='='){
					leerYAcumular(coord,fr,c,lexema);
				}
				break;
			case '<':
				if(fr.peek()=='>'){
					leerYAcumular(coord,fr,c,lexema);
				}else{
					if(fr.peek()=='='){
						leerYAcumular(coord,fr,c,lexema);
					}
				}
				break;
			case '>':
				if(fr.peek()=='='){
					leerYAcumular(coord,fr,c,lexema);
				}
		}
		coord.setX(coord.getX()+1);
		if(error){  //esto por ahora esta al pedo!!
			return new Error(coord.getY(), colmini, lexema.toString(), 2); 
		}else{
			if(OperadorBooleano.esOperadorBooleano(lexema.toString())){
				if(lexema.toString().equals("=") && this.last_token.getType() == TokenTypes.TIPO){
					return new Symbolo(coord.getY(),colmini,lexema.toString(),TokenTypes.CONST_IGUAL);
				}
				return new OperadorBooleano(coord.getY(),colmini,lexema.toString());
			}else{
				if(OperadorMatematico.esOperadorMatematico(lexema.toString())){
					try{
						return new OperadorMatematico(coord.getY(),colmini,lexema.toString());
					}catch(Exception e){
						Error err = new Error(coord.getY(),colmini,lexema.toString(),6,e.toString());
						return err;
					}
				}else{
					if(lexema.toString().equals(":=")){
						return new Symbolo(coord.getY(),colmini,":=",TokenTypes.ASIGNACION);
					}
				}
				return new Symbolo(coord.getY(), colmini, lexema.toString());
			}
		}
	}
	
	
	/**
	 * Devuelve un de tipo Palabra o Error si contiene errores lexicos  
	 * 
	 * @param coord Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	Objeto Caracter con el primer simbolo a tratar
	 * 
	 * @return 		Un token Palabra
	 */
	public Token tratarPalabra(Caracter c){      // mientras fin-mientras 
		lexema.setLength(0);
		int colmini=coord.getX();
		boolean evaluarFin = false;
		TablaDeSimbolos tabla = SingleTabla.getInstance();
		
		lexema.append((char)c.getSimbolo());
		//permite letras numeros o _
		while (fr.peek()!=EOF && !esCortePalabraONumero(fr.peek()) && (Character.isDigit(fr.peek()) || Character.isLetter(fr.peek()) || fr.peek()=='_'))  {
			leerYAcumular(coord,fr,c,lexema);
		}
		
		//Acumular posible palabra reservada fin- 
		if((new String("end").equals(lexema.toString())) && (fr.peek()=='-')){
			leerYAcumular(coord,fr,c,lexema);
			evaluarFin = true; 
			while((fr.peek()!=EOF && !esCortePalabraONumero( (fr.peek())))){
				leerYAcumular(coord,fr,c,lexema);
			}
		}
		coord.setX(coord.getX()+1);
		
		// Si sali del while por Palabras con simbolos no aceptados
		if(!Character.isDigit(fr.peek()) && !Character.isLetter(fr.peek()) && !esCortePalabraONumero(fr.peek()) ){
			while ((fr.peek()!=-1 && !esCortePalabraONumero( (fr.peek())))){
				leerYAcumular(coord,fr,c,lexema);
			}
			Error error = new Error(coord.getY(), colmini, lexema.toString(), 4);
			return error;
		}
		
		//Tratar posibles palabras reservadas con end- o error lexico
		if(evaluarFin){ 
			//if(fines.contains(lexema.toString())){
			if(tabla.searchPalabraReservada(lexema.toString()) != null){
				try{
					PalabraReservada p = new PalabraReservada(coord.getY(), colmini, lexema.toString());
					return p;
				}catch(Exception e){
					Error err = new Error(coord.getY(),colmini,lexema.toString(),6,e.toString());
					return err;
				}
			}else{
		        	Error error = new Error(coord.getY(), colmini, lexema.toString(), 5);
		        	return error;
		    }
		}else{	
			if(lexema.toString().equals("true") || lexema.toString().equals("false")){
				if(lexema.toString().equals("true")){
					return new Booleano(coord.getY(),colmini,true);
				}else{
					return new Booleano(coord.getY(),colmini,false);
				}
			}else{
				TokenTypes tipo = tabla.searchPalabraReservada(lexema.toString());
				if(tipo == TokenTypes.PALABRARESERVADA){
					try{
						PalabraReservada p = new PalabraReservada(coord.getY(), colmini, lexema.toString());
						return p;
					}catch(Exception e){
						Error err = new Error(coord.getY(),colmini,lexema.toString(),6,e.toString());
						return err;
					}
				}else{
					if(tipo == TokenTypes.TIPO){
						return new Tipo(coord.getY(),colmini,lexema.toString());
					}else{
						ID id = new ID(coord.getY(), colmini, lexema.toString());
						tabla.addIdentificador(id);
						return id;
					}
				}
			}
		}
	}

	
	/** Lee los caracteres de un error lexico y general el Token de Error    
	 * 
	 * @param coord Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	Objeto Caracter con el primer simbolo a tratar
	 * 
	 * @return 		Un token de Error con el lexema de error 
	 */
	public Error tratarError(Caracter caracter) {
		lexema.setLength(0);
		int colmini=coord.getX();
		
		lexema.append((char)caracter.getSimbolo());
		while ((fr.peek()!=-1 && !esCortePalabraONumero( (fr.peek()) ))){
			leerYAcumular(coord,fr,caracter,lexema);
		}
//		coord.setX(coord.getX()+1);
		Error error = new Error(coord.getY(), colmini, lexema.toString(), 4);
		return error;
	}
	
	
	/**
	 * Procedimiento que lee un caracter, actualiza las coordenadas del cursor y agrega el simbolo leido 
	 * al lexema 
	 * 
	 * @param coord  Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		 Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	 Objeto Caracter con el primer simbolo a tratar
	 * @param lexema Objeto que contendra el lexma del Token  
	 * 
	 */
	private static void leerYAcumular(Coordenadas coord, FileReader f, Caracter c, StringBuffer lexema)
	{
		c.setSimbolo(f.read());
		coord.setX(coord.getX()+1);
		lexema.append((char)c.getSimbolo());
	}
	
	/**
	 * Procedimiento que lee un caracter, actualiza las coordenadas del cursor y agrega el simbolo leido 
	 * al lexema  
	 * 
	 * @param coord  Objeto que mantiene las coordenadas del puntero en el File  
	 * @param f		 Objeto FileReader utilizado para moverse en el file y espiar el buffer 
	 * @param c 	 Objeto Caracter con el primer simbolo a tratar
	 * @param lexema Objeto que contendra el lexma del Token  
	 * 
	 */
	private static void leerYAcumularCadena(Coordenadas coord, FileReader fr, Caracter caracter, StringBuffer lexema)
	{
		caracter.setSimbolo(fr.readInsensitive());
		coord.setX(coord.getX()+1);  
		lexema.append((char)caracter.getSimbolo());
	}
	
	/**	
	 * @param s 	caracter con el simbolo a evaluar 
	 * @return true si el simbolo que se esta tratando representa el fin de un lexema de tipo Palabra o Numero 
	 */
	public boolean esCortePalabraONumero(int s) {
		if	(
				(s=='=') ||
				(s==':') ||
				(s==',') ||
				(s=='+') ||
				(s=='-') ||
				(s=='/') ||
				(s=='*') ||
				(s==';') ||
				(s=='(') ||
				(s==')') ||
				(s=='<') ||
				(s=='>') ||
				(s=='{') ||
				(s==' ') ||
				(s=='\'') ||
				(s=='\n') ||
				(s=='\t') ||
				(s=='[') ||
				(s==']') ||
				(s=='!') ||
				(Caracter.Ignorable(s))||
				(Caracter.esComentario(s)) ||
				(Caracter.esOperador(s)) ||
				(s == EOF)
			)
				return true;
		else
				return false;
	}
}
