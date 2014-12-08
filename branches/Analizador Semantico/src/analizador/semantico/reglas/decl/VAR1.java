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

public class VAR1 extends VARS implements ReglaSemantica{
	
	
	public VAR1() {
		super(VAR1.class.getSimpleName());
	}
	
	public VAR1(String n) {
		super(n);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod)throws SemanticException {
		if(prod==null){
			throw new AlgorithmicError("Error en la produccion VAR1","La produccion no puede estar vacia");
		}else{
			if(prod.size() == 2){
				return this.accionSemantica((analizador.lexico.tokens.Tipo)prod.get(0));
			}else{
				try{
					if(prod.size() == 3){
						Token t = (Token)prod.get(2);
						if(t.getType() == TokenTypes.COMMA){
							return this.accionSemantica((ID)prod.get(1),(VAR1)prod.get(0));
						}else{
							throw new AlgorithmicError("Falta la coma");
						}
					}else{
						throw new AlgorithmicError("Error en la produccion VAR1","El tamanio de la produccion es incorrecto");
					}
				}catch(ClassCastException e){
					throw new AlgorithmicError("Error algoritmico en la produccion VAR1", "La Produccion vino mal formada, se esperaba TERMBOOL");
				}catch(IndexOutOfBoundsException ex){
					throw new AlgorithmicError("Error algoritmico en la produccion VAR1", ex.getMessage());
				}
			}
		}
	}

	private boolean accionSemantica(analizador.lexico.tokens.Tipo tipo) {
		this.lista = new ArrayList<HojaVar>();
		this.setTipo(Tipo.parseTipo(tipo.getLexema()));
		return true;
	}

	private boolean accionSemantica(ID id, VAR1 var1) throws SemanticException {
		this.lista = var1.lista;
		this.setTipo(var1.getTipo());
		this.lista.add(0,new HojaVar(id.getLexema(),var1.getTipo()));
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		tabla.addVar(id.getLexema(), var1.getTipo());
		return true;
	}
}
