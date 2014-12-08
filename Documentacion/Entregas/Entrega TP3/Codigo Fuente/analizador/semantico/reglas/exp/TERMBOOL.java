package analizador.semantico.reglas.exp;

import java.util.Stack;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import exceptions.AlgorithmicError;
import exceptions.TypeException;
import analizador.lexico.tokens.PalabraReservada;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.exp.NodoExp;
import analizador.sintactico.estructuras.LRApilable;

public class TERMBOOL extends EXP implements ReglaSemantica {
	
	public TERMBOOL(){
		super(TERMBOOL.class.getSimpleName());
	}
	
	public TERMBOOL(String s){
		super(s);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) throws TypeException {
		if(prod==null){
			throw new AlgorithmicError("Error algoritmico en la produccion TERMBOOL", "La Produccion vino mal formada");
		}else{
			if(prod.size()==1){
				try{
					return this.accionSemantica((MINITERMBOOL)prod.get(0));
				}catch(ClassCastException e){
					throw new AlgorithmicError("Error algoritmico en la produccion TERMBOOL", "La Produccion vino mal formada");
				}catch(IndexOutOfBoundsException ex){
					throw new AlgorithmicError("Error algoritmico en la produccion TERMBOOL", ex.getMessage());
				}
			}else{
				if(prod.size()==3){
					try{
						PalabraReservada tkn = (PalabraReservada)prod.get(1);
						if(tkn.getType()==TokenTypes.AND){
							return this.accionSemantica((TERMBOOL)prod.get(2), tkn, (MINITERMBOOL)prod.get(0));
						}else{
							throw new AlgorithmicError("Error Sintactico en la produccion TERMBOOL", "Falta la suma o la resta");
						}
					}catch(ClassCastException e){
						throw new AlgorithmicError("Error algoritmico en la produccion TERMBOOL", "La Produccion vino mal formada");
					}catch(IndexOutOfBoundsException ex){
						throw new AlgorithmicError("Error algoritmico en la produccion TERMBOOL", ex.getMessage());
					}
				}else{
					throw new AlgorithmicError("Error algoritmico en la produccion TERMBOOL", "La Produccion vino mal formada");
				}
			}
		}
	}

	private boolean accionSemantica(TERMBOOL term, PalabraReservada tkn, MINITERMBOOL fact) throws TypeException {
		if(term.getTipo()==Tipo.BOOLEAN && fact.getTipo()==Tipo.BOOLEAN){
			this.setTipo(Tipo.BOOLEAN);
			this.setArbol(new NodoExp(term.getArbol(),NodoExp.TipoOperacion.AND ,fact.getArbol()));
			return true;
		}else{
			SingleTabla.getInstance().setErrorMode(); //Para que despues no genero codigo
			term.setTipo(Tipo.BOOLEAN);
			fact.setTipo(Tipo.BOOLEAN);
			throw new TypeException("Error semantico en la produccion TERMBOOL","Los tipos de variables a ambos lados de AND deben ser booleanos");
		}
	}

	private boolean accionSemantica(MINITERMBOOL miniterm) {
		this.setArbol(miniterm.getArbol());
		this.setTipo(miniterm.getTipo());
		return true;
	}
}
