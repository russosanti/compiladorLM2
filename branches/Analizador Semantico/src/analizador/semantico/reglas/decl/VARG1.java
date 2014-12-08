package analizador.semantico.reglas.decl;

import java.util.ArrayList;
import java.util.Stack;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import tabladesimbolos.iTablaDeSimbolos;
import analizador.lexico.tokens.Entero;
import analizador.lexico.tokens.ID;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.decl.HojaArray;
import analizador.semantico.tree.decl.HojaVar;
import analizador.sintactico.estructuras.LRApilable;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;

public class VARG1 extends VARG implements ReglaSemantica{
	
	private boolean isArray;
	private int tamano;
	

	public VARG1() {
		super(VARG1.class.getSimpleName());
	}
	
	public VARG1(String n) {
		super(n);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod)throws SemanticException {
		if(prod==null){
			throw new AlgorithmicError("Error en la produccion VARG1","La produccion no puede estar vacia");
		}else{
			if(prod.size() == 2){
				return this.accionSemantica((analizador.lexico.tokens.Tipo)prod.get(0));
			}else{
				try{
					if(prod.size() == 3){
						Token t = (Token)prod.get(2);
						if(t.getType() == TokenTypes.COMMA){
							return this.accionSemantica((ID)prod.get(1),(VARG1)prod.get(0));
						}else{
							throw new AlgorithmicError("Falta la coma");
						}
					}else{
						if(prod.size() == 4){
							Token t1 = (Token)prod.get(3);
							Token t2 = (Token)prod.get(1);
							if(t1.getType() == TokenTypes.CORCHETE_APERTURA && t2.getType() == TokenTypes.CORCHETE_CIERRE){
								return this.accionSemantica((Entero)prod.get(2),(VARG1)prod.get(0));
							}else{
								throw new AlgorithmicError("No se cerraron los []");
							}
						}else{
							throw new AlgorithmicError("Error en la produccion VARG1","El tamanio de la produccion es incorrecto");
						}
					}
				}catch(ClassCastException e){
					throw new AlgorithmicError("Error algoritmico en la produccion VARG1", "La Produccion vino mal formada, se esperaba TERMBOOL");
				}catch(IndexOutOfBoundsException ex){
					throw new AlgorithmicError("Error algoritmico en la produccion VARG1", ex.getMessage());
				}
			}
		}
	}

	private boolean accionSemantica(analizador.lexico.tokens.Tipo tipo) {
		this.lista = new ArrayList<HojaVar>();
		this.isArray = false;
		this.setTipo(Tipo.parseTipo(tipo.getLexema()));
		return true;
	}

	private boolean accionSemantica(ID id, VARG1 varg1) throws SemanticException {
		this.lista = varg1.lista;
		this.setTipo(varg1.getTipo());
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		if(varg1.isArray){
			this.lista.add(0, new HojaArray(id.getLexema(),varg1.getTipo(),varg1.getTamano()));
			tabla.addArray(id.getLexema(), varg1.getTipo(), varg1.getTamano());
		}else{
			this.lista.add(0,new HojaVar(id.getLexema(),varg1.getTipo()));
			tabla.addVarGlobales(id.getLexema(), varg1.getTipo());
		}
		this.isArray = false;
		return true;
	}

	private boolean accionSemantica(Entero entero, VARG1 varg1) {
		this.lista = varg1.lista;
		this.isArray = true;
		this.tamano = entero.getLexema();
		this.setTipo(varg1.getTipo());
		return true;
	}
	
	public boolean isArray() {
		return isArray;
	}

	public int getTamano() {
		return tamano;
	}
}
