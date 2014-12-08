package analizador.semantico.reglas.exp;

import java.util.Stack;

import exceptions.AlgorithmicError;
import exceptions.SemanticException;
import exceptions.TypeException;
import analizador.lexico.tokens.PalabraReservada;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.exp.NodoExp;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;
import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;

public class EXP extends NoTerminal implements ReglaSemantica{
	
	private NodoExp arbol;
	private Tipo tipo;
	
	public EXP(){
		super(EXP.class.getSimpleName());
	}
	
	public EXP(String n) {
		super(n);
	}
	
	public NodoExp getArbol(){
		return this.arbol;
	}
	
	protected void setArbol(NodoExp nodo){
		this.arbol = nodo;
	}
	
	public Tipo getTipo(){
		return this.tipo;
	}
	
	protected void setTipo(Tipo t){
		this.tipo = t;
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) throws SemanticException {
		if(prod==null){
			throw new AlgorithmicError("Error algoritmico en la produccion EXP", "La Produccion vino mal formada");
		}else{
			if(prod.size()==1){
				try{
					return this.accionSemantica((TERMBOOL)prod.get(0));
				}catch(ClassCastException e){
					throw new AlgorithmicError("Error algoritmico en la produccion EXP", "La Produccion vino mal formada, se esperaba TERMBOOL");
				}catch(IndexOutOfBoundsException ex){
					throw new AlgorithmicError("Error algoritmico en la produccion EXP", ex.getMessage());
				}
			}else{
				if(prod.size()==3){
					try{
						PalabraReservada tkn = (PalabraReservada)prod.get(1);
						if(tkn.getType()==TokenTypes.OR){
							return this.accionSemantica((EXP)prod.get(2), tkn, (TERMBOOL)prod.get(0));
						}else{
							throw new AlgorithmicError("Error Sintactico en la produccion EXP", "Falta la suma o la resta");
						}
					}catch(ClassCastException e){
						throw new AlgorithmicError("Error algoritmico en la produccion EXP", "La Produccion vino mal formada");
					}catch(IndexOutOfBoundsException ex){
						throw new AlgorithmicError("Error algoritmico en la produccion EXP", ex.getMessage());
					}
				}else{
					throw new AlgorithmicError("Error algoritmico en la produccion EXP", "La Produccion vino mal formada");
				}
			}
		}
	}

	private boolean accionSemantica(EXP exp, PalabraReservada tkn, TERMBOOL term) throws TypeException {
		if(exp.getTipo()==Tipo.BOOLEAN && term.getTipo()==Tipo.BOOLEAN){
			this.setTipo(Tipo.BOOLEAN);
			this.setArbol(new NodoExp(exp.getArbol(),NodoExp.TipoOperacion.OR ,term.getArbol()));
			return true;
		}else{
			SingleTabla.getInstance().setErrorMode(); //Para que despues no genero codigo
			exp.setTipo(Tipo.BOOLEAN); //para que tire error en 1 solo lado
			term.setTipo(Tipo.BOOLEAN);
			throw new TypeException("Error semantico en la produccion EXP","Los tipos de variables a ambos lados de AND deben ser booleanos");
		}
	}

	private boolean accionSemantica(TERMBOOL term) {
		this.setArbol(term.getArbol());
		this.setTipo(term.getTipo());
		return true;
	}
}
