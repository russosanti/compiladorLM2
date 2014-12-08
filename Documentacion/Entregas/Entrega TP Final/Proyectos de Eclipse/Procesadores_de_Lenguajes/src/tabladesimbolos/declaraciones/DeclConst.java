package tabladesimbolos.declaraciones;

import interfaz.UI;
import tabladesimbolos.Tipo;

public class DeclConst extends Declaraciones{
	private int valorInt;
	private boolean valorBool;
	
	public DeclConst(String nombre, Tipo tipo, String valor) {
		super(TipoDeclaracion.CONST, nombre,tipo);
		if(tipo == Tipo.BOOLEAN){
			if(valor.equalsIgnoreCase("true")){
				this.valorBool = true;
			}else{
				this.valorBool = false;
			}
		}else{
			if(tipo == Tipo.INTEGER){
				this.valorInt = Integer.parseInt(valor);
			}else{
				UI.error("Error! Tipo no soportado por la tabla de Simbolos. Se abortara la compilacion");
				UI.exit();
			}
		}
	}
	
	public DeclConst(TipoDeclaracion tipoDeclaracion, String nombre, int valor) {
		super(tipoDeclaracion, nombre,Tipo.INTEGER);
		this.valorInt = valor;
	}
	
	public DeclConst(TipoDeclaracion tipoDeclaracion, String nombre, boolean valor) {
		super(tipoDeclaracion, nombre,Tipo.BOOLEAN);
		this.valorBool = valor;
	}
	
	public String getValor(){
		if(this.getTipo() == Tipo.BOOLEAN){
			return String.valueOf(this.valorBool);
		}else{
			return String.valueOf(this.valorInt);
		}
	}
	
	public int getValorInt(){
		return this.valorInt;
	}
	
	public boolean getValorBool(){
		return this.valorBool;
	}
}