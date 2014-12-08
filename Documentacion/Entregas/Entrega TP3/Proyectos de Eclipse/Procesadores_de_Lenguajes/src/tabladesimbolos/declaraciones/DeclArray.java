package tabladesimbolos.declaraciones;

import tabladesimbolos.Tipo;

public class DeclArray extends Declaraciones{
	private int tamano;
	
	public DeclArray(String nombre, Tipo tipo, int n) {
		super(TipoDeclaracion.ARRAY, nombre,tipo);
		this.tamano = n;
	}
	
	public int getTamano(){
		return this.tamano;
	}
}
