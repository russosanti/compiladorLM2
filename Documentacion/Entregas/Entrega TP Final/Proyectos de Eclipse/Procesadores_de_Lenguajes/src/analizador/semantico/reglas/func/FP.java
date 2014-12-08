package analizador.semantico.reglas.func;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import tabladesimbolos.iTablaDeSimbolos;
import analizador.lexico.tokens.ID;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.reglas.bloque.BLOQUE1;
import analizador.semantico.reglas.exp.EXP;
import analizador.semantico.tree.bloque.NodoBloque1;
import analizador.semantico.tree.decl.NodoDecl;
import analizador.semantico.tree.func.HojaParam;
import analizador.semantico.tree.func.NodoFunc;
import analizador.semantico.tree.func.NodoParam;
import analizador.semantico.tree.func.NodoProc;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;
import exceptions.TypeException;
import generadorCodigo.ParameterRetainer;

public class FP extends NoTerminal implements ReglaSemantica{
	
	private NodoProc nodo;
	
	public FP() {
		super(FP.class.getSimpleName());
	}
	
	public FP(String n) {
		super(n);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) throws SemanticException{
		if(prod==null || prod.size()==0){
			throw new AlgorithmicError("Error en el ASIG","La produccion no puede estar vacia");
		}else{
			try{
				if(prod.size()==9){
					Token t = (Token)prod.get(8);
					if(t.getType()==TokenTypes.PROCEDURE){
						return this.accionSemantica((ID)prod.get(7),(PARAM)prod.get(5),(BLOQUE1)prod.get(2));
					}else{
						throw new AlgorithmicError("Error algoritmico en la produccion FP", "Falta la palabra reservada procedure");
					}
				}else{
					if(prod.size()==12){
						Token t = (Token)prod.get(11);
						if(t.getType()==TokenTypes.FUNCTION){
							return this.accionSemantica((ID)prod.get(10),(PARAM)prod.get(8),(analizador.lexico.tokens.Tipo)prod.get(5),(BLOQUE1)prod.get(3),(EXP)prod.get(1));
						}else{
							throw new AlgorithmicError("Error algoritmico en la produccion FP", "Falta la palabra reservada procedure");
						}
					}else{
						throw new AlgorithmicError("Error algoritmico en la produccion FP", "La cantidad esta mal");
					}
				}
			}catch(ClassCastException e){
				throw new AlgorithmicError("Error algoritmico en la produccion FP", "La Produccion vino mal formada");
			}catch(IndexOutOfBoundsException ex){
				throw new AlgorithmicError("Error algoritmico en la produccion FP", ex.getMessage());
			}
		}
	}

	private boolean accionSemantica(ID id, PARAM param, BLOQUE1 bloque1) throws SemanticException {
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		tabla.setContexto(id.getLexema());
		if(tabla.prohibitedID(id.getLexema())){
			throw new SemanticException("El procedimiento " + id.getLexema() + " no puede ser utilizado como declaracion de"
					+ " una funcion.", "Palabra interna reservada del Lenguaje LM2");
		}else{
			if(tabla.declaredFunc(id.getLexema())){
				throw new SemanticException("El procedimiento " + id.getLexema() + " ya ha sido declarado");
			}else{
				this.nodo = new NodoProc(id.getLexema(),new NodoParam(param.getLista()),new NodoDecl(bloque1.getDecl()),
						new NodoBloque1(bloque1.getBloque()));
				
				//Agrego los parametros al retenedor
				ParameterRetainer.getInstance().addParams(id.getLexema(), param.getLista());
				
				Iterator<HojaParam> it = param.getLista().iterator();
				
				HojaParam aux;
				ArrayList<Tipo> tipoParam = new ArrayList<Tipo>();
				while(it.hasNext()){
					aux = it.next();
					tipoParam.add(aux.getTipo());
				}
				tabla.addProc(id.getLexema(),tipoParam);
				return true;
			}
		}
	}

	private boolean accionSemantica(ID id, PARAM param, analizador.lexico.tokens.Tipo tipo,BLOQUE1 bloque1, EXP exp) throws SemanticException {
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		tabla.setContexto(id.getLexema());
		if(tabla.declaredFunc(id.getLexema())){
			throw new SemanticException("El procedimiento " + id.getLexema() + " ya ha sido declarado");
		}else{
			Tipo ti =Tipo.parseTipo(tipo.getLexema());
			if(ti==exp.getTipo()){
				this.nodo = new NodoFunc(id.getLexema(),new NodoParam(param.getLista()),new NodoDecl(bloque1.getDecl()),
						new NodoBloque1(bloque1.getBloque()),ti,exp.getArbol());
				
				//Agrego los parametros al retenedor
				ParameterRetainer.getInstance().addParams(id.getLexema(), param.getLista());
				
				Iterator<HojaParam> it = param.getLista().iterator();
				HojaParam aux;
				ArrayList<Tipo> tipoParam = new ArrayList<Tipo>();
				while(it.hasNext()){
					aux = it.next();
					tipoParam.add(aux.getTipo());
				}
				tabla.addFunc(id.getLexema(),tipoParam,ti);
				return true;
			}else{
				throw new TypeException("El tipo declarado en la funcion no concuerda con el tipo que se devuelve",
						"Se declaro "+ ti + " pero se devuelve "+exp.getTipo());
			}
		}
	}

	public NodoProc getNodo() {
		return nodo;
	}	
}
