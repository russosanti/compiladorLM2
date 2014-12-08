package tabladesimbolos.declaraciones;

import java.util.ArrayList;

import tabladesimbolos.Tipo;
import analizador.lexico.tokens.ID;

public class DeclFuncProcDesconocidas extends Declaraciones{
	
	private ArrayList<Tipo> tipo;
	private ID idToken;
	
	public DeclFuncProcDesconocidas(String id, ArrayList<Tipo> tipo, ID idToken) {
		super(id,Tipo.VOID);
		this. tipo = tipo;
		this.idToken = idToken;
	}
	
	public DeclFuncProcDesconocidas(String id, ArrayList<Tipo> tipo, ID idToken, Tipo tipoRetorno) {
		super(id, tipoRetorno);
		this. tipo = tipo;
		this.idToken = idToken;
	}

	public ArrayList<Tipo> getTipoParams() {
		return tipo;
	}

	public ID getToken() {
		return idToken;
	}
}
