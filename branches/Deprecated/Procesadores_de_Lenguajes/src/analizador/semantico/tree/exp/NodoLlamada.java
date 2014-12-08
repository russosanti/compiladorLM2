package analizador.semantico.tree.exp;

import analizador.lexico.tokens.ID;
import analizador.semantico.tree.bloque.Sentencias;

public class NodoLlamada extends NodoAcceso implements Sentencias{
	
	private NodoPasaje parametros;
	
	public NodoLlamada(String id, NodoPasaje pasaje){
		super(id);
		this.parametros = pasaje;
		this.tipoNodo = TipoNodo.LLAMADA;
	}
	
	public NodoLlamada(String id, NodoPasaje pasaje,ID tkn){
		super(id,tkn);
		this.parametros = pasaje;
		this.tipoNodo = TipoNodo.LLAMADA;
	}
}
