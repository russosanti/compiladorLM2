package analizador.semantico.reglas.func;

import java.util.ArrayList;
import java.util.Stack;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import tabladesimbolos.iTablaDeSimbolos;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;
import exceptions.TableException;
import analizador.lexico.tokens.ID;
import analizador.lexico.tokens.Token;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.func.HojaParam;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;

public class PARAM extends NoTerminal implements ReglaSemantica{
	
	private ArrayList<HojaParam> lista;
	
	public PARAM() {
		super(PARAM.class.getSimpleName());
	}
	
	public PARAM(String n) {
		super(n);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod)throws SemanticException {
		if (prod==null || prod.size()==0){
			this.lista = new ArrayList<HojaParam>();
		}else{
			if (prod.size()==5){
				
				try {
					PARAM1 param1 = (PARAM1)prod.get(0);
					Token tipo = (Token)prod.get(1);
					ID id = (ID)prod.get(3);
					TIPOPARAM tipoParam = (TIPOPARAM)prod.get(4);
					accionSemantica(param1,tipo,id,tipoParam);
				} catch (ClassCastException e) {
					throw new AlgorithmicError("Error en PARAM");
				}
				
			}else throw new AlgorithmicError("Error algoritmico en la produccion PARAM", "La cantidad de elementos es erronea");
		}
		return true;
	}

	private void accionSemantica(PARAM1 param1, Token tipo, ID id,TIPOPARAM tipoParam) throws SemanticException {
		//Se pasa el booleano de isByRef en el constructor de HojaParam
		this.lista=param1.getLista();
		this.lista.add(0,new HojaParam(tipoParam.isByRef(),id.getLexema(),Tipo.parseTipo(tipo.getStringLex())));
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		try {
			tabla.addParam(id.getLexema(), Tipo.parseTipo(tipo.getStringLex()), tipoParam.isByRef());
		} catch (TableException e) {
			new AlgorithmicError("Error agregando el Parametro " + id.getLexema() + " a la tabla de simbolos", e.getMessage());
		}
	}

	public ArrayList<HojaParam> getLista(){
		return this.lista;
	}
	
}
