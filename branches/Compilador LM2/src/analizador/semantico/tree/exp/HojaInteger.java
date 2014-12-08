package analizador.semantico.tree.exp;

import generadorCodigo.TemporalManager;
import analizador.lexico.tokens.Entero;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class HojaInteger extends Hoja{ //esto lo hago para poder asignarlos
	
	private int valorInt; 
	
	public HojaInteger(Entero bool){
		super(bool);
		this.valorInt = bool.getLexema();
		this.tipoNodo = TipoNodo.HOJAINTEGER;
	}
	
	@Override
	/** Mueve el valor del entero a AX */
	public void generateCode() {
		ITree cg = ExecutionTree.getInstance();
		cg.insertCode("MOV AX,"+this.valorInt);
		if(this.needsTemporal()){
			TemporalManager tm = TemporalManager.getInstance();
			String temp = tm.getTemporal();
			this.setTemporalUsed(temp);
			cg.insertCode("MOV " + temp +",AX");
		}
	}

	public int getValorInt() {
		return valorInt;
	}
}
