package analizador.semantico.reglas.bloque;

import java.util.ArrayList;
import java.util.Stack;

import tabladesimbolos.Tipo;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;
import exceptions.TypeException;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.reglas.exp.EXP;
import analizador.semantico.reglas.exp.PASAJE;
import analizador.semantico.tree.exp.NodoExp;
import analizador.semantico.tree.exp.NodoPasaje;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;

public class ASIG extends NoTerminal implements ReglaSemantica{
	
	private TipoAsig tipoNodo;
	private NodoExp arbolExp;
	private NodoPasaje arbolPasaje;
	private Tipo tipo;
	private ArrayList<Tipo> tipoPasaje;
	private NodoExp indice;
	
	public ASIG() {
		super(ASIG.class.getSimpleName());
	}
	
	public ASIG(String n) {
		super(n);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod)throws SemanticException {
		if(prod==null || prod.size()==0){
			throw new AlgorithmicError("Error en el ASIG","La produccion no puede estar vacia");
		}else{
			try{
				if(prod.size()==2){
					//ASIG -> ASIGNACION <EXP>
					return this.accionSemantica((EXP)prod.get(0));
				}else{
					if(prod.size()==3){
						//ASIG -> PARENTESIS_APERTURA <PASAJE> PARENTESIS_CIERRE
						Token t1 = (Token)prod.get(2);
						Token t2 = (Token)prod.get(0);
						if(t1.getType()==TokenTypes.PARENTESIS_APERTURA && t2.getType()==TokenTypes.PARENTESIS_CIERRE){
							return this.accionSemantica((PASAJE)prod.get(1));
						}else{
							throw new AlgorithmicError("Error algoritmico en la produccion ASIG", "Los parametros deben estar rodeados por ()");
						}
					}else{
						if(prod.size()==5){
							//ASIG -> CORCHETE_APERTURA <EXP'> CORCHETE_CIERRE ASIGNACION <EXP''>
							Token t1 = (Token)prod.get(4);
							Token t2 = (Token)prod.get(2);
							if(t1.getType()==TokenTypes.CORCHETE_APERTURA && t2.getType()==TokenTypes.CORCHETE_CIERRE){
								return this.accionSemantica((EXP)prod.get(3),(EXP)prod.get(0));
							}else{
								throw new AlgorithmicError("Error algoritmico en la produccion ASIG", "Los parametros deben estar rodeados por ()");
							}
						}else{
							throw new AlgorithmicError("Error algoritmico en la produccion ASIG", "La cantiad es Incorrecta");
						}
					}
				}
			}catch(ClassCastException e){
				throw new AlgorithmicError("Error algoritmico en la produccion ASIG", "La Produccion vino mal formada");
			}catch(IndexOutOfBoundsException ex){
				throw new AlgorithmicError("Error algoritmico en la produccion ASIG", ex.getMessage());
			}
		}
	}
	
	private boolean accionSemantica(EXP exp) {
		this.tipoNodo = TipoAsig.ACCESO;
		this.arbolExp = exp.getArbol();
		this.tipo = exp.getTipo();
		return true;
	}

	private boolean accionSemantica(PASAJE pasaje) {
		this.tipoNodo = TipoAsig.LLAMADA;
		this.arbolPasaje = pasaje.getArbol();
		this.tipoPasaje = pasaje.getTipoParam();
		return true;
	}

	private boolean accionSemantica(EXP indice, EXP exp) throws TypeException {
		if(indice.getTipo()==Tipo.INTEGER){
			this.tipoNodo = TipoAsig.ARRAY;
			this.indice = indice.getArbol();
			this.arbolExp = exp.getArbol();
			this.tipo = exp.getTipo();
			return true;
		}else{
			throw new TypeException("El tipo del indice de un arreglo debe ser integer");
		}
	}

	public TipoAsig getTipoNodo() {
		return tipoNodo;
	}


	public Tipo getTipo() {
		return tipo;
	}


	public ArrayList<Tipo> getTipoPasaje() {
		return tipoPasaje;
	}


	public NodoExp getIndice() {
		return indice;
	}

	public NodoExp getArbolExp() {
		return arbolExp;
	}


	public NodoPasaje getArbolPasaje() {
		return arbolPasaje;
	}


	public enum TipoAsig{
		LLAMADA,ACCESO,ARRAY;
	}
	
}
