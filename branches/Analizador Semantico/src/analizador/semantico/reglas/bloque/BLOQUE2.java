package analizador.semantico.reglas.bloque;

import java.util.ArrayList;
import java.util.Stack;

import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.bloque.Sentencias;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;

public class BLOQUE2 extends NoTerminal implements ReglaSemantica{
	
	protected ArrayList<Sentencias> lista;
	
	public BLOQUE2() {
		super(BLOQUE2.class.getSimpleName());
	}
	
	public BLOQUE2(String n) {
		super(n);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod)throws SemanticException {
		if(prod==null || prod.size()==0){
			return this.accionSemantica();
		}else{
			if(prod.size()==3){
				try{
					Token t = (Token)prod.get(1);
					if(t.getType()==TokenTypes.ENDLINE){
						return this.accionSemantica((LINEA)prod.get(2),(BLOQUE2)prod.get(0));
					}else{
						throw new AlgorithmicError("Error algoritmico en la produccion BLOQUE2", "Falta el ; al final de una lina");
					}
				}catch(ClassCastException e){
					throw new AlgorithmicError("Error algoritmico en la produccion BLOQUE2", "La Produccion vino mal formada");
				}catch(IndexOutOfBoundsException ex){
					throw new AlgorithmicError("Error algoritmico en la produccion BLOQUE2", ex.getMessage());
				}
			}else{
				throw new AlgorithmicError("Error algoritmico en la produccion BLOQUE2", "La cantidad de la produccion es incorrecta");
			}
		}
	}

	private boolean accionSemantica() {
		this.lista = new ArrayList<Sentencias>();
		return true;
	}

	private boolean accionSemantica(LINEA linea, BLOQUE2 bloque) {
		this.lista = bloque.lista;
		this.lista.add(0,linea.getArbol());
		return true;
	}

	public ArrayList<Sentencias> getLista() {
		return lista;
	}
}
