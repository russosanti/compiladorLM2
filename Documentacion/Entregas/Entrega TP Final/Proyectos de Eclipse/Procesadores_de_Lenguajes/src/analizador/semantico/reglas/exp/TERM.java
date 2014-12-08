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

public class TERM extends EXP implements ReglaSemantica {
	
	public TERM(){
		super(TERM.class.getSimpleName());
	}
	
	public TERM(String s){
		super(s);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) throws TypeException {
		if(prod==null){
			throw new AlgorithmicError("Error algoritmico en la produccion TERM", "La Produccion vino mal formada");
		}else{
			if(prod.size()==1){
				try{
					return this.accionSemantica((FACT)prod.get(0));
				}catch(ClassCastException e){
					throw new AlgorithmicError("Error algoritmico en la produccion TERM", "La Produccion vino mal formada");
				}catch(IndexOutOfBoundsException ex){
					throw new AlgorithmicError("Error algoritmico en la produccion TERM", ex.getMessage());
				}
			}else{
				if(prod.size()==3){
					try{
						OperadorMatematico tkn = (OperadorMatematico)prod.get(1);
						if(tkn.getType()==TokenTypes.MULT_DIV){
							return this.accionSemantica((TERM)prod.get(2), tkn, (FACT)prod.get(0));
						}else{
							throw new AlgorithmicError("Error Sintactico en la produccion TERM", "Falta la suma o la resta");
						}
					}catch(ClassCastException e){
						throw new AlgorithmicError("Error algoritmico en la produccion TERM", "La Produccion vino mal formada");
					}catch(IndexOutOfBoundsException ex){
						throw new AlgorithmicError("Error algoritmico en la produccion TERM", ex.getMessage());
					}
				}else{
					throw new AlgorithmicError("Error algoritmico en la produccion TERM", "La Produccion vino mal formada");
				}
			}
		}
	}

	private boolean accionSemantica(TERM term, OperadorMatematico tkn, FACT fact) throws TypeException {
		if(term.getTipo()==Tipo.INTEGER && fact.getTipo()==Tipo.INTEGER){
			this.setTipo(Tipo.INTEGER);
			if(tkn.getLexema().equalsIgnoreCase("*")){
				this.setArbol(new NodoExp(term.getArbol(),NodoExp.TipoOperacion.PROD ,fact.getArbol()));
				return true;
			}else{
				if(tkn.getLexema().equalsIgnoreCase("/")){
					this.setArbol(new NodoExp(term.getArbol(),NodoExp.TipoOperacion.DIV ,fact.getArbol()));
					return true;
				}else{
					throw new AlgorithmicError("Error algoritmico en la produccion TERM", "El lexema no es ni un * ni un /");
				}
			}
		}else{
			SingleTabla.getInstance().setErrorMode(); //Para que despues no genero codigo
			term.setTipo(Tipo.INTEGER);
			fact.setTipo(Tipo.INTEGER);
			throw new TypeException("Error semantico en la produccion TERM","Los tipos de variables a ambos lados de una * o / deben ser enteros");
		}
	}

	private boolean accionSemantica(FACT fact) {
		this.setArbol(fact.getArbol());
		this.setTipo(fact.getTipo());
		return true;
	}
}
