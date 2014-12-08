package analizador.semantico.reglas.exp;

import java.util.ArrayList;
import java.util.Stack;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.Tipo;
import exceptions.AlgorithmicError;
import exceptions.TypeException;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.INodo.TipoNodo;
import analizador.semantico.tree.exp.NodoExp;
import analizador.semantico.tree.exp.NodoPasaje;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;

public class ID1 extends NoTerminal implements ReglaSemantica {
	
	private NodoPasaje arbolPasaje;
	private NodoExp arbolExp;
	private TipoNodo tipoNodo;
	private ArrayList<Tipo> tipos;
	
	public NodoPasaje getArbolPasaje() {
		return arbolPasaje;
	}

	public void setArbolPasaje(NodoPasaje arbolPasaje) {
		this.arbolPasaje = arbolPasaje;
	}

	public NodoExp getArbolExp() {
		return arbolExp;
	}

	public void setArbolExp(NodoExp arbolExp) {
		this.arbolExp = arbolExp;
	}

	public TipoNodo getTipoNodo() {
		return tipoNodo;
	}

	public void setTipoNodo(TipoNodo tipoNodo) {
		this.tipoNodo = tipoNodo;
	}

	public ID1(){
		super(ID1.class.getSimpleName());
	}
	
	public ID1(String s){
		super(s);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) throws TypeException {
		if (prod!=null && prod.size()==3){
			try{
				Token tkn = (Token)prod.get(2);
				if (tkn.getType()==TokenTypes.CORCHETE_APERTURA){
					return accionSemantica((EXP)prod.get(1));
				}else{
					if (tkn.getType()==TokenTypes.PARENTESIS_APERTURA){
						return accionSemantica((PASAJE)prod.get(1));
					}else{
						throw new AlgorithmicError("Error algoritmico en la produccion ID1");
					}
				}
			}catch(ClassCastException e){
				throw new AlgorithmicError("Error algoritmico en la produccion ID1", "La Produccion vino mal formada");
			}catch(IndexOutOfBoundsException ex){
				throw new AlgorithmicError("Error algoritmico en la produccion ID1", ex.getMessage());
			}
			
		}else{
			if (prod==null || prod.size()==0){
				return accionSemantica();
			}
		}
		return true;
	}

	private boolean accionSemantica(PASAJE pasaje) {
		this.tipoNodo = TipoNodo.LLAMADA;
		this.arbolPasaje = pasaje.getArbol();
		this.tipos = pasaje.getTipoParam();
		return true;
	}

	private boolean accionSemantica() {
		this.tipoNodo = TipoNodo.ACCESO;
		return true;
	}

	private boolean accionSemantica(EXP exp) throws TypeException {
		if (exp.getTipo()!=Tipo.INTEGER){
			SingleTabla.getInstance().setErrorMode(); //Para que despues no genero codigo
			exp.setTipo(Tipo.INTEGER); //para que siga ejecutando sin tantos errores
			throw new TypeException("La expresion dentro de un arreglo debe ser un Tipo INTEGER");
		}
		this.tipoNodo = TipoNodo.ACCESOARRAY;
		this.arbolExp = exp.getArbol();
		return true;
	}

	public ArrayList<Tipo> getTipos() {
		return tipos;
	}
}
