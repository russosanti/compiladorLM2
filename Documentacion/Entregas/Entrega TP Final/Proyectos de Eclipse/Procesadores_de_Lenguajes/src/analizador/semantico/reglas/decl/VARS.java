package analizador.semantico.reglas.decl;

import java.util.ArrayList;
import java.util.Stack;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import tabladesimbolos.iTablaDeSimbolos;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;
import analizador.lexico.tokens.ID;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.decl.HojaVar;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;

public class VARS extends NoTerminal implements ReglaSemantica{
	
	protected ArrayList<HojaVar> lista;
	private Tipo tipo;
	
	public VARS() {
		super(VARS.class.getSimpleName());
	}
	
	public VARS(String n) {
		super(n);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod)throws SemanticException {
		if(prod==null){
			throw new AlgorithmicError("Error en la produccion VARS","La produccion no puede estar vacia");
		}else{
			try{
				if(prod.size() == 4){
					Token t1 = (Token)prod.get(3);
					Token t2 = (Token)prod.get(0);
					if(t1.getType() == TokenTypes.VAR && t2.getType() == TokenTypes.ENDLINE){
						return this.accionSemantica((ID)prod.get(2),(VAR1)prod.get(1));
					}else{
						throw new AlgorithmicError("Falta la declaracion de VAR o el ;");
					}
				}else{
					throw new AlgorithmicError("Error en la produccion VARS","El tamanio de la produccion es incorrecto");
				}
			}catch(ClassCastException e){
				throw new AlgorithmicError("Error algoritmico en la produccion VARS", "La Produccion vino mal formada");
			}catch(IndexOutOfBoundsException ex){
				throw new AlgorithmicError("Error algoritmico en la produccion VARG", ex.getMessage());
			}
		}
	}

	private boolean accionSemantica(ID id, VAR1 var1) throws SemanticException {
		this.lista = var1.lista;
		this.setTipo(var1.getTipo());
		this.lista.add(0,new HojaVar(id.getLexema(),var1.getTipo()));
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		tabla.addVar(id.getLexema(), var1.getTipo());
		return true;
	}

	public Tipo getTipo() {
		return tipo;
	}

	protected void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
}
