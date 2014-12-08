package analizador.semantico.reglas.exp;

import java.util.Stack;

import exceptions.AlgorithmicError;
import exceptions.TypeException;
import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.exp.NodoExpBool;
import analizador.semantico.tree.exp.NodoExpBool.TipoOperacion;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;

public class EXPBOOL extends NoTerminal implements ReglaSemantica {
	
	private NodoExpBool arbol;
	private Tipo tipo;
	
	public EXPBOOL(){
		super(EXPBOOL.class.getSimpleName());
	}
	
	public EXPBOOL(String s){
		super(s);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) throws TypeException {
		if(prod != null && prod.size()==3){
			try{
				Token tok = (Token)prod.get(1);
				if(tok.getType()==TokenTypes.OPERADOR_BOOLEANO){
					return accionSemantica((EXP2)prod.get(2),(EXP2)prod.get(0));
				}else{
					if(tok.getType()==TokenTypes.OPERADOR_BOOLEANO_E){
						return accionSemantica2((EXP2)prod.get(2),(EXP2)prod.get(0));
					}else{
						throw new AlgorithmicError("Error algoritmico en la produccion EXPBOOL", "La Produccion vino mal formada");
					}
				}
			}catch(ClassCastException e){
				throw new AlgorithmicError("Error algoritmico en la produccion EXPBOOL", "La Produccion vino mal formada");
			}catch(IndexOutOfBoundsException ex){
				throw new AlgorithmicError("Error algoritmico en la produccion EXPBOOL", ex.getMessage());
			}
		}else{
			throw new AlgorithmicError("Error en la Produccion EXPBOOL", "Pila mal formada");
		}
	}
	
	private boolean accionSemantica(EXP2 exp, EXP2 exp2) throws TypeException{
		if(exp.getTipo() != exp2.getTipo()){
			SingleTabla.getInstance().setErrorMode(); //Para que despues no genero codigo
			exp2.setTipo(exp.getTipo()); //Para que tire error en un solo lado
			throw new TypeException("Error Semantico en la comparacion =","Los tipos a ambos lados de la igualdad no coinciden");
		}else{
			this.arbol = new NodoExpBool(exp.getArbol(),TipoOperacion.IGUALDAD,exp2.getArbol());
			this.tipo = Tipo.BOOLEAN;
			return true;
		}
	}
	
	private boolean accionSemantica2(EXP2 exp, EXP2 exp2) throws TypeException{
		if(exp.getTipo() != Tipo.INTEGER || exp2.getTipo() != Tipo.INTEGER){
			SingleTabla.getInstance().setErrorMode(); //Para que despues no genero codigo
			exp.setTipo(Tipo.INTEGER); //Para que tire error en un solo lado
			exp2.setTipo(Tipo.INTEGER);
			throw new TypeException("Error Semantico en la comparacion >","Los tipos a ambos lados de la desigualdad deben ser enteros");
		}else{
			this.arbol = new NodoExpBool(exp.getArbol(),TipoOperacion.MAYOR_MENOR,exp2.getArbol());
			this.tipo = Tipo.BOOLEAN;
			return true;
		}
	}

	public NodoExpBool getArbol() {
		return arbol;
	}


	public Tipo getTipo() {
		return tipo;
	}
}
