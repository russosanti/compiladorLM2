package analizador.semantico.reglas.decl;

import java.util.ArrayList;
import java.util.Stack;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import tabladesimbolos.iTablaDeSimbolos;
import analizador.lexico.tokens.ID;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.decl.HojaConst;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;
import exceptions.TypeException;

public class CONSTS extends NoTerminal implements ReglaSemantica{
	
	protected ArrayList<HojaConst> lista;
	
	public CONSTS() {
		super(CONSTS.class.getSimpleName());
	}
	
	public CONSTS(String n) {
		super(n);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod)throws SemanticException {
		if(prod == null || prod.size()==0){
			throw new AlgorithmicError("Error algoritmico en CONSTS", "La produccion esta vacia o no existe");
		}else{
			if(prod.size()==5){
				try{
					Token t = (Token)prod.get(4);
					if(t.getType()==TokenTypes.CONST){
						//CONSTS -> CONST ID DEF_TIPO <CONST1> ENDLINE
						return this.accionSemantica((ID)prod.get(3),(CONST1)prod.get(1));
					}else{
						throw new AlgorithmicError("Error algoritmico en CONSTS", "Falta la coma");
					}
				}catch(ClassCastException e){
					throw new AlgorithmicError("Error algoritmico en la produccion CONSTS", "La Produccion esta mal formada");
				}catch(IndexOutOfBoundsException ex){
					throw new AlgorithmicError("Error algoritmico en la produccion CONSTS", ex.getMessage());
				}
			}else{
				throw new AlgorithmicError("Error algoritmico en la produccion CONSTS", "La cantidad de elementos es erronea");
			}

		}
	}

	private boolean accionSemantica(ID id, CONST1 const1) throws SemanticException {
		this.lista = const1.lista;
		if(const1.getTipo()==Tipo.BOOLEAN){
			this.lista.add(0,new HojaConst(id.getLexema(),const1.getValBool()));
		}else{
			if(const1.getTipo()==Tipo.INTEGER){
				this.lista.add(0,new HojaConst(id.getLexema(),const1.getValInt()));
			}else{
				throw new TypeException("El tipo de la variable " + id + "no es valido");
			}
		}
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		if(tabla.isGlobalContext()){
			tabla.addConstGlobales(id.getLexema(), const1.getTipo(), const1.getVal());
		}else{
			tabla.addConst(id.getLexema(), const1.getTipo(), const1.getVal());
		}
		return true;
	}
	
}
