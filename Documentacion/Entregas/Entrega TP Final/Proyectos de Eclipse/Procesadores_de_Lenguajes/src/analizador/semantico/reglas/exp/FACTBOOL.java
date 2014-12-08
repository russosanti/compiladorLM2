package analizador.semantico.reglas.exp;

import java.util.Stack;
import tabladesimbolos.Tipo;
import exceptions.AlgorithmicError;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.sintactico.estructuras.LRApilable;

public class FACTBOOL extends EXP implements ReglaSemantica {
	
	public FACTBOOL(){
		super(FACTBOOL.class.getSimpleName());
	}
	
	public FACTBOOL(String s){
		super(s);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) {
		if(prod!=null){
			if(prod.size()==1){
				try{
					return this.accionSemantica((EXPBOOL)prod.get(0));
				}catch(ClassCastException ce){//si hay error xq no es EXPBOOL
					try{
						return this.accionSemantica((EXP2)prod.get(0));
					}catch(ClassCastException cce){// si no es EXP2
						throw new AlgorithmicError("Error algoritmico por error Sintactico", "La produccion vine con un no terminal erroneo");
					}
				}catch(IndexOutOfBoundsException ie){
					throw new AlgorithmicError("Error algoritmico en la produccion FACTBOOL", "La Produccion vino vaciaa");
				}
			}else{
				throw new AlgorithmicError("Error algoritmico en la produccion FACTBOOL", "La Produccion vino con menos o mas de un no terminal");
			}
		}else{
			throw new AlgorithmicError("Error algoritmico en la produccion FACTBOOL", "La Produccion vino con menos o mas de un no terminal");
		}
	}

	private boolean accionSemantica(EXPBOOL expbool) {
		this.setArbol(expbool.getArbol());
		this.setTipo(Tipo.BOOLEAN);
		return true;
	}

	private boolean accionSemantica(EXP2 exp2) {
		this.setArbol(exp2.getArbol());
		this.setTipo(exp2.getTipo());
		return true;
	}
}
