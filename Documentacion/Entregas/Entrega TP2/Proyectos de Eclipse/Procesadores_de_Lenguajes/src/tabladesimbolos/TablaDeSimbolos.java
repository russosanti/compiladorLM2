package tabladesimbolos;

import interfaz.UI;
import java.util.Iterator;
import analizador.lexico.tokens.ID;
import analizador.lexico.tokens.TokenTypes;

/**Clase de la tabla de simbolos
 * @author Santy */
public class TablaDeSimbolos implements iTablaDeSimbolos{
	private TablaPalabrasReservadas t_palabras;
	private TablaID t_ID;
	
	/** Constructor */
	public TablaDeSimbolos(){
		this.t_palabras = new TablaPalabrasReservadas();
		this.t_ID = new TablaID();
	}
	
	/**Agrega un identificador a la tabla de ID's */
	public boolean addIdentificador(ID p){
		return this.t_ID.add(p);
	}
	
	/** Busca en todas las tablas si existe */
	public boolean searchAll(String s){
		return (this.t_palabras.existTipo(s) || this.t_palabras.existPalabraReservada(s) || this.t_ID.existeID(s));
	}
	
	/** Busca si existe como Palabra reservada o como tipo y retorna de que tipo es.
	 * O null si no lo encuentra*/
	public TokenTypes searchPalabraReservada(String palabra){
		TokenTypes tipo = null;
		if(this.t_palabras.existPalabraReservada(palabra)){
			tipo = TokenTypes.PALABRARESERVADA;
		}else{
			if(this.t_palabras.existTipo(palabra)){
				tipo = TokenTypes.TIPO;
			}
		}
		return tipo;
	}
	
	/** Busca si existe como Identificador */
	public boolean searchIdentificador(String s){
		return this.t_ID.existeID(s);
	}
	
	public void showIDs(){
		System.out.println("Declaraciones: ");
		Iterator<String> i = this.t_ID.getKeyIterator();
		while(i.hasNext()){
			UI.informar(i.next());
		}
	}
	
}

