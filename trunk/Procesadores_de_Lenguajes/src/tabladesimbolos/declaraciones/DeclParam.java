package tabladesimbolos.declaraciones;

import tabladesimbolos.Tipo;

public class DeclParam extends Declaraciones{
	
	private boolean byRef;
	
	public DeclParam(String nombre, Tipo tipo, boolean byref){
		super(TipoDeclaracion.PARAM, nombre, tipo);
		this.byRef = byref;
	}
	
	public boolean isByRef(){
		return this.byRef;
	}
}
