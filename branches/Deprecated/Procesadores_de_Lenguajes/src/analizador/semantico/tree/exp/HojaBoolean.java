package analizador.semantico.tree.exp;

import analizador.lexico.tokens.Booleano;
import analizador.semantico.tree.INodo.TipoNodo;

public class HojaBoolean extends Hoja{ //esto lo hago para poder asignarlos
	
	private boolean valorBool; 
	
	public HojaBoolean(Booleano bool){
		super(bool);
		this.valorBool = bool.getLexema();
		this.tipoNodo = TipoNodo.HOJABOOLEAN;
	}
}
