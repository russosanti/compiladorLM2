package analizador.semantico.tree.exp;

import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;
import tabladesimbolos.Tipo;

public abstract class Hoja extends NodoExp{ //esto lo hago para poder asignarlos
	/** Tipo de Variable que contiene */
	
	private Tipo tipo;
	@SuppressWarnings("unused")
	private Token tkn;
	private String valor;
	
	protected Hoja(Token tkn){
		if(tkn.getType() == TokenTypes.BOOLEANO){
			this.tipo = Tipo.BOOLEAN;
			this.tkn = tkn;
			if(tkn.getStringLex().equalsIgnoreCase("true")){
				this.valor = "1";
			}else{
				this.valor = "0";
			}
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
	
	public String getValor(){
		return this.valor;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" Tipo: "+this.getTipo()+ " Valor: "+this.valor;
	}
	
	@Override
	public void generateCode() {
		// No genera codigo
	}
}
