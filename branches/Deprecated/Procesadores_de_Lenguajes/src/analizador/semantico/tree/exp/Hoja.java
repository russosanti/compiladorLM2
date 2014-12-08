package analizador.semantico.tree.exp;

import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import tabladesimbolos.Tipo;

public abstract class Hoja extends NodoExp{ //esto lo hago para poder asignarlos
	/** Tipo de Variable que contiene */
	
	private Tipo tipo;
	private Token tkn;
	private String valor;
	
	protected Hoja(Token tkn){
		if(tkn.getType() == TokenTypes.BOOLEANO){
			this.tipo = Tipo.BOOLEAN;
			this.tkn = tkn;
			this.valor = tkn.getStringLex();
		}
		else{
			if(tkn.getType() == TokenTypes.NUMERO){
				this.tipo = Tipo.INTEGER;
				this.tkn = tkn;
				this.valor = tkn.getStringLex();
			}else{
				this.tipo = Tipo.STRING;
				this.tkn = tkn;
				this.valor = tkn.getStringLex();
			}
		}
	}
	
	public Tipo getTipo(){
		return this.tipo;
	}
}
