package analizador.semantico.reglas.exp;

import java.util.Stack;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import exceptions.AlgorithmicError;
import exceptions.TypeException;
import analizador.lexico.tokens.PalabraReservada;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.exp.NodoNot;
import analizador.sintactico.estructuras.LRApilable;

public class MINITERMBOOL extends EXP implements ReglaSemantica {
	
	public MINITERMBOOL(){
		super(MINITERMBOOL.class.getSimpleName());
	}
	
	public MINITERMBOOL(String s){
		super(s);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) throws TypeException {
		if(prod==null){
			throw new AlgorithmicError("Error algoritmico en la produccion MINITERMBOOL", "La Produccion esta vacia");
		}else{
			if(prod.size()==1){
				try{
					return this.accionSemantica((FACTBOOL)prod.get(0));
				}catch(ClassCastException e){
					throw new AlgorithmicError("Error algoritmico en la produccion MINITERMBOOL", "La Produccion no contiene el FACTBOOL");
				}catch(IndexOutOfBoundsException ex){
					throw new AlgorithmicError("Error algoritmico en la produccion MINITERMBOOL", ex.getMessage());
				}
			}else{
				if(prod.size()==2){
					try{
						PalabraReservada tkn = (PalabraReservada)prod.get(1);
						if(tkn.getType()==TokenTypes.NOT){
							return this.accionSemantica((MINITERMBOOL)prod.get(0));
						}else{
							throw new AlgorithmicError("Error Sintactico en la produccion MINITERMBOOL", "Falta la suma o la resta");
						}
					}catch(ClassCastException e){
						throw new AlgorithmicError("Error algoritmico en la produccion MINITERMBOOL", "La Produccion vino mal formada");
					}catch(IndexOutOfBoundsException ex){
						throw new AlgorithmicError("Error algoritmico en la produccion MINITERMBOOL", ex.getMessage());
					}
				}else{
					throw new AlgorithmicError("Error algoritmico en la produccion MINITERMBOOL", "La Produccion vino mal formada");
				}
			}
		}
	}

	private boolean accionSemantica(MINITERMBOOL minitermbool) throws TypeException {
		if(minitermbool.getTipo() == Tipo.BOOLEAN){
			this.setArbol(new NodoNot(minitermbool.getArbol()));
			this.setTipo(Tipo.BOOLEAN);
			return true;
		}else{
			SingleTabla.getInstance().setErrorMode(); //Para que despues no genero codigo
			minitermbool.setTipo(Tipo.BOOLEAN);
			throw new TypeException("Error en el not","La variable que sigue al not debe ser de tipo booleana");
		}
	}

	private boolean accionSemantica(FACTBOOL factbool) {
		this.setArbol(factbool.getArbol());
		this.setTipo(factbool.getTipo());
		return true;
	}
}
