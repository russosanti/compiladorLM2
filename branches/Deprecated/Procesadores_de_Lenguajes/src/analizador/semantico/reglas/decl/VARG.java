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
import analizador.semantico.tree.decl.HojaArray;
import analizador.semantico.tree.decl.HojaVar;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;

public class VARG extends NoTerminal implements ReglaSemantica{
	
	protected ArrayList<HojaVar> lista;
	private Tipo tipo;
	
	public VARG() {
		super(VARG.class.getSimpleName());
	}
	
	public VARG(String n) {
		super(n);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod)throws SemanticException {
		if(prod==null){
			throw new AlgorithmicError("Error en la produccion VARG","La produccion no puede estar vacia");
		}else{
			try{
				if(prod.size() == 4){
					Token t1 = (Token)prod.get(3);
					Token t2 = (Token)prod.get(0);
					if(t1.getType() == TokenTypes.VAR && t2.getType() == TokenTypes.ENDLINE){
						return this.accionSemantica((ID)prod.get(2),(VARG1)prod.get(1));
					}else{
						throw new AlgorithmicError("Falta la declaracion de VAR o el ;");
					}
				}else{
					throw new AlgorithmicError("Error en la produccion VARG","El tamanio de la produccion es incorrecto");
				}
			}catch(ClassCastException e){
				throw new AlgorithmicError("Error algoritmico en la produccion VARG", "La Produccion vino mal formada, se esperaba TERMBOOL");
			}catch(IndexOutOfBoundsException ex){
				throw new AlgorithmicError("Error algoritmico en la produccion VARG", ex.getMessage());
			}
		}
	}

	private boolean accionSemantica(ID id, VARG1 varg1) throws SemanticException {
		this.lista = varg1.lista;
		this.setTipo(varg1.getTipo());
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		if(varg1.isArray()){
			this.lista.add(0, new HojaArray(id.getLexema(),varg1.getTipo(),varg1.getTamano()));
			tabla.addArray(id.getLexema(), varg1.getTipo(), varg1.getTamano());
		}else{
			this.lista.add(0,new HojaVar(id.getLexema(),varg1.getTipo()));
			tabla.addVarGlobales(id.getLexema(), varg1.getTipo());
		}
		return true;
	}

	public Tipo getTipo() {
		return tipo;
	}

	protected void setTipo(Tipo t) {
		this.tipo = t;
	}

	public ArrayList<HojaVar> getLista() {
		return lista;
	}
}
