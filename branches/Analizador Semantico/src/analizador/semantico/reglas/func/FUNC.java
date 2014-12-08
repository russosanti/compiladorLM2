package analizador.semantico.reglas.func;

import java.util.Stack;
import analizador.lexico.tokens.ID;
import analizador.lexico.tokens.Token;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.reglas.bloque.IDAUX;
import analizador.semantico.reglas.bloque.MOSTRAR;
import analizador.semantico.tree.func.NodoRead;
import analizador.semantico.tree.func.NodoShow;
import analizador.semantico.tree.func.iFuncList;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;

public class FUNC extends NoTerminal implements ReglaSemantica{
	
	private iFuncList arbol;
	
	public FUNC() {
		super(FUNC.class.getSimpleName());
	}
	
	public FUNC(String n) {
		super(n);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod)throws SemanticException {
		
		if (prod==null || prod.size()==0){
			throw new AlgorithmicError("Error algoritmico en la produccion TIPOPARAM", "La cantidad de elementos es erronea");
		}else{
			
			if (prod.size()==2){
				try {
					//FUNC -> SHOW <MOSTRAR>
					MOSTRAR mostrar = (MOSTRAR)prod.get(0);
					Token show = (Token)prod.get(1);
					return accionSemantica(mostrar,show);
				} catch (ClassCastException e) {				
					throw new AlgorithmicError("Error en FUNC");
				}
			}else{
				if (prod.size()==3){
					//FUNC -> READ ID <IDAUX>
					try {
						IDAUX idaux = (IDAUX)prod.get(0);
						ID id = (ID)prod.get(1);
						Token read = (Token)prod.get(2);
						return accionSemantica(idaux,id,read);
					} catch (ClassCastException e) {				
						throw new AlgorithmicError("Error en FUNC");
					}				
				}else throw new AlgorithmicError("Error algoritmico en la produccion FUNC", "La cantidad de elementos es erronea");
			}			
		}
	}

	private boolean accionSemantica(IDAUX idaux, ID id, Token read) {
		if (idaux.isArray()){
			this.arbol = new NodoRead(id.getStringLex(),idaux.getIndice());	
		}else{
			this.arbol = new NodoRead(id.getStringLex());	
		}
		return true;
	}

	private boolean accionSemantica(MOSTRAR mostrar, Token show) {
		this.arbol = new NodoShow(show.getStringLex(),mostrar.getLista());
		return true;
	}

	public iFuncList getArbol() {
		return arbol;
	}
	
}
