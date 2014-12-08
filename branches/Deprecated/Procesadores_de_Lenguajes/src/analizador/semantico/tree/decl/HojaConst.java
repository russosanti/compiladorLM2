package analizador.semantico.tree.decl;

import tabladesimbolos.Tipo;

public class HojaConst extends HojaVar{

	private String valor;
	private boolean valorBool;
	private int valorInt;
	
	public HojaConst(String s, Tipo t, String val) {
		super(s, t);
		this.valor = val;
		if(t==Tipo.BOOLEAN){
			this.valorBool = Boolean.parseBoolean(val);
		}else{
			this.valorInt = Integer.parseInt(val);
		}
	}
	
	public HojaConst(String s, boolean val) {
		super(s, Tipo.BOOLEAN);
		this.valor = String.valueOf(val);
		this.valorBool = val;
	}
	
	public HojaConst(String s, int val) {
		super(s, Tipo.INTEGER);
		this.valor = String.valueOf(val);
		this.valorInt = val;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.HOJACONST;
	}

	public String getValor() {
		return valor;
	}

	public boolean getValorBool() {
		return valorBool;
	}

	public int getValorInt() {
		return valorInt;
	}
}
