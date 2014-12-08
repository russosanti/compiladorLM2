package analizador.semantico.reglas.decl;

import java.util.Stack;

import tabladesimbolos.Tipo;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;
import exceptions.TypeException;
import analizador.lexico.tokens.Booleano;
import analizador.lexico.tokens.Entero;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.sintactico.estructuras.LRApilable;

public class CONST1 extends CONSTS implements ReglaSemantica{
	
	private Tipo tipo;
	private String val;
	private boolean valBool;
	private int valInt;
	
	public CONST1() {
		super(CONST1.class.getSimpleName());
	}
	
	public CONST1(String n) {
		super(n);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod)throws SemanticException {
		if(prod==null){
			throw new AlgorithmicError("Error algoritmico en la produccion CONST1", "La cantidad esta mal");
		}else{
			if(prod.size()==4){
				try{
					analizador.lexico.tokens.Tipo tip = (analizador.lexico.tokens.Tipo)prod.get(3);
					Token tok = (Token)prod.get(1);
					//CONST1 -> TIPO CONST_IGUAL BOOLEANO <CONST2>
					if(tok.getType()==TokenTypes.BOOLEANO){
						Booleano bo = (Booleano)tok;
						return this.accionSemantica(tip,bo,(CONST2)prod.get(0));
					}else{
						//CONST1 -> TIPO CONST_IGUAL NUMERO <CONST2>
						if(tok.getType()==TokenTypes.NUMERO){
							Entero en = (Entero)tok;
							return this.accionSemantica(tip,en,(CONST2)prod.get(0));
						}else{
							throw new AlgorithmicError("Error algoritmico en la produccion CONST1", "El tipo de declaracion es invalido");
						}
					}
				}catch(ClassCastException e){
					throw new AlgorithmicError("Error algoritmico en la produccion CONST1", "La Produccion vino mal formada");
				}catch(IndexOutOfBoundsException ex){
					throw new AlgorithmicError("Error algoritmico en la produccion CONST1", ex.getMessage());
				}
			}else{
				throw new AlgorithmicError("Error algoritmico en la produccion CONST1", "La cantidad esta mal");
			}
		}
	}

	private boolean accionSemantica(analizador.lexico.tokens.Tipo tip,Booleano bo, CONST2 const2) throws TypeException{
		if(Tipo.parseTipo(tip.getLexema())==Tipo.BOOLEAN){
			this.tipo = Tipo.BOOLEAN;
			this.setValBool(bo.getLexema());
			this.lista = const2.lista;
			return true;
		}else{
			throw new TypeException("Error en el tipo al definir una constante Booleana");
		}
	}

	private boolean accionSemantica(analizador.lexico.tokens.Tipo tip,Entero en, CONST2 const2) throws TypeException {
		if(Tipo.parseTipo(tip.getLexema())==Tipo.INTEGER){
			this.tipo = Tipo.INTEGER;
			this.setValInt(en.getLexema());
			this.lista = const2.lista;
			return true;
		}else{
			throw new TypeException("Error en el tipo al definir una constante Booleana");
		}
	}

	public Tipo getTipo() {
		return tipo;
	}

	public String getVal() {
		return val;
	}

	public boolean getValBool() {
		return valBool;
	}

	public int getValInt() {
		return valInt;
	}
	
	public void setValBool(boolean b) {
		this.valBool = b;
		this.val = String.valueOf(b);
	}

	public void setValInt(int e) {
		this.valInt = e;
		this.val = String.valueOf(e);
	}
}
