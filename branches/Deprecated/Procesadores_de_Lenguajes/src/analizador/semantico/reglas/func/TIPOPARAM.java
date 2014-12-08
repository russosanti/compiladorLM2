package analizador.semantico.reglas.func;

import java.util.Stack;

import analizador.lexico.tokens.Token;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;

public class TIPOPARAM extends NoTerminal implements ReglaSemantica{
	
	private boolean byRef;
	
	public TIPOPARAM() {
		super(TIPOPARAM.class.getSimpleName());
	}
	
	public TIPOPARAM(String n) {
		super(n);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) throws SemanticException {
		if (prod==null || prod.size()==0 ){
			this.byRef=false;
		}else{
			if (prod.size()==1){				
				try {
					Token by = (Token)prod.get(0);
					accionSemantica(by);
				} catch (ClassCastException e) {
					throw new AlgorithmicError("Error en TIPOPARAM");
				}			
			}else throw new AlgorithmicError("Error algoritmico en la produccion TIPOPARAM", "La cantidad de elementos es erronea");				
		}
		return true;
	}

	private void accionSemantica(Token by) {
		if (by.getStringLex().equalsIgnoreCase("byRef")){
			this.byRef=true;
		}else{
			this.byRef=false;
		}		
	}

	public boolean isByRef() {
		return byRef;
	}

	public void setByRef(boolean byRef) {
		this.byRef = byRef;
	}
	
}
