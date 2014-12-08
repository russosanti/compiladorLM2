package analizador.semantico.reglas.bloque;

import java.util.ArrayList;
import java.util.Stack;

import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.reglas.decl.DECL;
import analizador.semantico.tree.bloque.Sentencias;
import analizador.semantico.tree.decl.HojaDecl;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;

public class BLOQUE1 extends NoTerminal implements ReglaSemantica{
	
	private ArrayList<HojaDecl> decl;
	private ArrayList<Sentencias> bloque;
	
	public BLOQUE1() {
		super(BLOQUE1.class.getSimpleName());
		System.out.println("OK");
	}
	
	public BLOQUE1(String n) {
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
					if(t.getType()==TokenTypes.BEGIN){
						return this.accionSemantica((DECL)prod.get(2),(BLOQUE2)prod.get(0));
					}else{
						throw new AlgorithmicError("Error algoritmico en la produccion BLOQUE1", "Falta el begin al inicio de las sentencias");
					}
				}catch(ClassCastException e){
					throw new AlgorithmicError("Error algoritmico en la produccion BLOQUE1", "La Produccion vino mal formada");
				}catch(IndexOutOfBoundsException ex){
					throw new AlgorithmicError("Error algoritmico en la produccion BLOQUE1", ex.getMessage());
				}
			}else{
				throw new AlgorithmicError("Error algoritmico en la produccion BLOQUE1", "La cantidad de la produccion es incorrecta");
			}
		}
	}

	private boolean accionSemantica() {
		this.decl = new ArrayList<HojaDecl>();
		this.bloque = new ArrayList<Sentencias>();
		return true;
	}

	private boolean accionSemantica(DECL decl, BLOQUE2 bloque2) {
		this.decl = decl.getLista();
		this.bloque = bloque2.getLista();
		return true;
	}

	public ArrayList<HojaDecl> getDecl() {
		return decl;
	}

	public ArrayList<Sentencias> getBloque() {
		return bloque;
	}
	
}
