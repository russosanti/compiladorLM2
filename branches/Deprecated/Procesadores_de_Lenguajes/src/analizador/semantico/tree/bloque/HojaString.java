package analizador.semantico.tree.bloque;

import analizador.lexico.tokens.Cadena;
import analizador.semantico.tree.exp.Hoja;

public class HojaString extends Hoja implements Mostrable{ //esto lo hago para poder asignarlos
	
	private String valorString; 
	
	public HojaString(Cadena cadena){
		super(cadena);
		this.valorString = cadena.getLexema();
		this.tipoNodo = TipoNodo.HOJAINTEGER;
	}
}
