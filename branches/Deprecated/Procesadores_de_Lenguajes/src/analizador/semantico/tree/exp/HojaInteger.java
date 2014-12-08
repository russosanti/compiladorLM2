package analizador.semantico.tree.exp;

import analizador.lexico.tokens.Entero;
import analizador.semantico.tree.INodo.TipoNodo;

public class HojaInteger extends Hoja{ //esto lo hago para poder asignarlos
	
	private int valorInt; 
	
	public HojaInteger(Entero bool){
		super(bool);
		this.valorInt = bool.getLexema();
		this.tipoNodo = TipoNodo.HOJAINTEGER;
	}
}
