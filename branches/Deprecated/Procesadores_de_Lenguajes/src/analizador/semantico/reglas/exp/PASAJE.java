package analizador.semantico.reglas.exp;

import java.util.ArrayList;
import java.util.Stack;

import tabladesimbolos.Tipo;
import exceptions.AlgorithmicError;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.exp.NodoPasaje;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;

public class PASAJE extends NoTerminal implements ReglaSemantica {
	
	protected NodoPasaje arbol;
	protected ArrayList<Tipo> tipos;
	
	public PASAJE(){
		super(PASAJE.class.getSimpleName());
	}
	
	public PASAJE(String s){
		super(s);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) {
		if (prod == null || prod.size() == 0){
			return accionSemantica();
		}else{
			if (prod.size()==2){
				try {
					return accionSemantica((EXP)prod.get(1),(PASAJE1)prod.get(0));
				} catch (AlgorithmicError e) {
					throw new AlgorithmicError("Error en la accion semantica de PASAJE1");
				}
			}
		}
		return true;
	}
	
	private boolean accionSemantica(EXP exp, PASAJE1 pasaje1) {
		this.arbol=pasaje1.getArbol();
		this.arbol.addFirst(exp.getArbol());
		this.tipos = pasaje1.tipos;
		this.tipos.add(0, exp.getTipo());
		return true;
	}

	private boolean accionSemantica(){
		this.arbol = new NodoPasaje();
		this.tipos = new ArrayList<Tipo>();
		return true;
	}
	

	public NodoPasaje getArbol() {
		return arbol;
	}

	protected void setArbol(NodoPasaje arbol) {
		this.arbol = arbol;
	}
	
	public ArrayList<Tipo> getTipoParam(){
		return this.tipos;
	}
}
