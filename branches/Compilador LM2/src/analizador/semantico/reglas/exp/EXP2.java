package analizador.semantico.reglas.exp;

import java.util.Stack;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import exceptions.AlgorithmicError;
import exceptions.TypeException;
import analizador.lexico.tokens.OperadorMatematico;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.exp.NodoExp;
import analizador.sintactico.estructuras.LRApilable;

public class EXP2 extends EXP implements ReglaSemantica {
	
	public EXP2(){
		super(EXP2.class.getSimpleName());
	}
	
	public EXP2(String s){
		super(s);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) throws TypeException {
		if(prod==null){
			throw new AlgorithmicError("Error algoritmico en la produccion EXP2", "La Produccion vino mal formada");
		}else{
			if(prod.size()==1){
				try{
					return this.accionSemantica((TERM)prod.get(0));
				}catch(ClassCastException e){
					throw new AlgorithmicError("Error algoritmico en la produccion EXP2", "La Produccion vino mal formada");
				}catch(IndexOutOfBoundsException ex){
					throw new AlgorithmicError("Error algoritmico en la produccion EXP2", ex.getMessage());
				}
			}else{
				if(prod.size()==3){
					try{
						OperadorMatematico tkn = (OperadorMatematico)prod.get(1);
						if(tkn.getType()==TokenTypes.SUMA_RESTA){
							return this.accionSemantica((EXP2)prod.get(2), tkn, (TERM)prod.get(0));
						}else{
							throw new AlgorithmicError("Error Sintactico en la produccion EXP2", "Falta la suma o la resta");
						}
					}catch(ClassCastException e){
						throw new AlgorithmicError("Error algoritmico en la produccion EXP2", "La Produccion vino mal formada");
					}catch(IndexOutOfBoundsException ex){
						throw new AlgorithmicError("Error algoritmico en la produccion EXP2", ex.getMessage());
					}
				}else{
					throw new AlgorithmicError("Error algoritmico en la produccion EXP2", "La Produccion vino mal formada");
				}
			}
		}
	}

	private boolean accionSemantica(EXP2 exp2, OperadorMatematico tkn, TERM term) throws TypeException {
		if(exp2.getTipo()==Tipo.INTEGER && term.getTipo()==Tipo.INTEGER){
			this.setTipo(Tipo.INTEGER);
			if(tkn.getLexema().equalsIgnoreCase("+")){
				this.setArbol(new NodoExp(exp2.getArbol(),NodoExp.TipoOperacion.SUM ,term.getArbol()));
				return true;
			}else{
				if(tkn.getLexema().equalsIgnoreCase("-")){
					this.setArbol(new NodoExp(exp2.getArbol(),NodoExp.TipoOperacion.REST ,term.getArbol()));
					return true;
				}else{
					throw new AlgorithmicError("Error algoritmico en la produccion EXP2", "El lexema no es ni un + ni un -");
				}
			}
		}else{
			SingleTabla.getInstance().setErrorMode(); //Para que despues no genero codigo
			exp2.setTipo(Tipo.INTEGER); //Para que tire error en un solo lado
			term.setTipo(Tipo.INTEGER);
			throw new TypeException("Error semantico en la produccion EXP2","Los tipos de variables a ambos lados de una + o - deben ser enteros");
		}
	}

	private boolean accionSemantica(TERM term) {
		this.setArbol(term.getArbol());
		this.setTipo(term.getTipo());
		return true;
	}
}
