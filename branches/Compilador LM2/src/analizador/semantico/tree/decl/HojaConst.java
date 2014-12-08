package analizador.semantico.tree.decl;

import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;
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
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" ID: " +this.getId() +" Tipo: "+this.getTipo()+" Valor: "+this.getValor();
	}
	
	@Override
	public void generateCode() {
		//No genero nada xq luego reemplazo
	}
}
