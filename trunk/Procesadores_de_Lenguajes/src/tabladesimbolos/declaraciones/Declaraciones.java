package tabladesimbolos.declaraciones;

import tabladesimbolos.Tipo;

public abstract class Declaraciones {
	
	private String id;
	protected TipoDeclaracion tipoDeclaracion;
	private Tipo tipo; // en FUNC y PROC es lo que devuelve

	
	public Declaraciones(String id, Tipo tipoRetorno) {
		this.id = id;
		this.tipo = tipoRetorno;
	}
	
	public Declaraciones(TipoDeclaracion td, String nombre, Tipo tipo){
		this.id = nombre;
		this.tipo = tipo;
		this.tipoDeclaracion = td;
	}
	
	public String getID() {
		return this.id;
	}

	public TipoDeclaracion getTipoDeclaracion() {
		return tipoDeclaracion;
	}

	public Tipo getTipo() {
		return tipo;
	}
	
	public String toString(){
		return this.tipoDeclaracion.toString() +" Nombre: " + this.id + " Tipo: " + this.tipo;
	}
}
