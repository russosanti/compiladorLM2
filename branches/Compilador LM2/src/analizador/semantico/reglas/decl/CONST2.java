package analizador.semantico.reglas.decl;

import java.util.ArrayList;
import java.util.Stack;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import tabladesimbolos.iTablaDeSimbolos;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;
import exceptions.TypeException;
import analizador.lexico.tokens.ID;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.decl.HojaConst;
import analizador.sintactico.estructuras.LRApilable;

public class CONST2 extends CONSTS implements ReglaSemantica{
	
	
	public CONST2() {
		super(CONST2.class.getSimpleName());
	}
	
	public CONST2(String n) {
		super(n);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod)throws SemanticException {
		if(prod == null || prod.size()==0){
			//CONST2 -> λ
			return this.accionSemantica();
		}else{
			if(prod.size()==4){
				try{
					Token t = (Token)prod.get(3);
					if(t.getType()==TokenTypes.COMMA){
						//CONST2 -> COMMA ID DEF_TIPO <CONST1>
						return this.accionSemantica((ID)prod.get(2),(CONST1)prod.get(0));
					}else{
						throw new AlgorithmicError("Error algoritmico en CONST2", "Falta la coma");
					}
				}catch(ClassCastException e){
					throw new AlgorithmicError("Error algoritmico en la produccion CONST2", "La Produccion vino mal formada");
				}catch(IndexOutOfBoundsException ex){
					throw new AlgorithmicError("Error algoritmico en la produccion CONST2", ex.getMessage());
				}
			}else{
				throw new AlgorithmicError("Error algoritmico en la produccion CONST2", "La cantidad esta mal");
			}

		}
	}

	private boolean accionSemantica(ID id, CONST1 const1) throws SemanticException {
		this.lista = const1.lista;
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		if(const1.getTipo()==Tipo.BOOLEAN){
			this.lista.add(0,new HojaConst(id.getLexema(),const1.getValBool()));
		}else{
			if(const1.getTipo()==Tipo.INTEGER){
				this.lista.add(0,new HojaConst(id.getLexema(),const1.getValInt()));
			}else{
				throw new TypeException("El tipo de la variable " + id + "no es valido");
			}
		}
		if(tabla.isGlobalContext()){
			tabla.addConstGlobales(id.getLexema(), const1.getTipo(), const1.getVal());
		}else{
			tabla.addConst(id.getLexema(), const1.getTipo(), const1.getVal());
		}
		return true;
	}

	private boolean accionSemantica() {
		this.lista = new ArrayList<HojaConst>();
		return true;
	}

}
