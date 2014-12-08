package tabladesimbolos.declaraciones;

import java.util.ArrayList;

import tabladesimbolos.Tipo;

public class DeclFuncProc extends Declaraciones{
	private ArrayList<Tipo> param;
	
	//funciones
	public DeclFuncProc(TipoDeclaracion tipoDeclaracion, String nombre, ArrayList<Tipo> parametros, Tipo retorno) {
		super(tipoDeclaracion, nombre,retorno);
		this.param = parametros;
	}
	
	//procedimientos
	public DeclFuncProc(TipoDeclaracion tipoDeclaracion, String nombre, ArrayList<Tipo> parametros) {
		super(tipoDeclaracion, nombre,Tipo.VOID);
		this.param = parametros;
	}
	
	//funciones
	public DeclFuncProc(TipoDeclaracion tipoDeclaracion, String nombre, Tipo retorno) {
		super(tipoDeclaracion, nombre,retorno);
		this.param = new ArrayList<Tipo>();
	}
	
	//procedimientos
	public DeclFuncProc(TipoDeclaracion tipoDeclaracion, String nombre) {
		super(tipoDeclaracion, nombre,Tipo.VOID);
		this.param = new ArrayList<Tipo>();
	}
	
	public ArrayList<Tipo> getParametros(){
		return this.param;
	}
	
	public Tipo getRetorno(){
		return this.getTipo();
	}
}
