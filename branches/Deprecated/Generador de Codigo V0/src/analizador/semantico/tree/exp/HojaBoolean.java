package analizador.semantico.tree.exp;

import generadorCodigo.TemporalManager;
import analizador.lexico.tokens.Booleano;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class HojaBoolean extends Hoja{ //esto lo hago para poder asignarlos
	
	private int valorBool; 
	
	public HojaBoolean(Booleano bool){
		super(bool);
		if(bool.getLexema()){
			this.valorBool = 1;
		}else{
			this.valorBool = 0;
		}
		this.tipoNodo = TipoNodo.HOJABOOLEAN;
	}
	
	@Override
	public void generateCode() {
		ITree cg = ExecutionTree.getInstance();
		cg.insertCode("MOV AX,"+this.valorBool);
		if(this.needsTemporal()){
			TemporalManager tm = TemporalManager.getInstance();
			String temp = tm.getTemporal();
			this.setTemporalUsed(temp);
			cg.insertCode("MOV " + temp +",AX");
		}
	}

	public int getValorBool() {
		return valorBool;
	}
}
