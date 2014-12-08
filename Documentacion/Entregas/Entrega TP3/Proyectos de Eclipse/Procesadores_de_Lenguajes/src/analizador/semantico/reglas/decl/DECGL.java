package analizador.semantico.reglas.decl;

import java.util.ArrayList;
import java.util.Stack;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.decl.HojaDecl;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;

public class DECGL extends NoTerminal implements ReglaSemantica{

	
	private ArrayList<HojaDecl> lista;
	
	
	public DECGL() {
		super(DECGL.class.getSimpleName());
	}	
	
	public DECGL(String decgl) {
		super(decgl);
	}

	
	public boolean accionSemantica(Stack<LRApilable> prod) throws SemanticException{

		if (prod== null || prod.size()==0){
			this.lista = new ArrayList<HojaDecl>();
		}else{
			if (prod.size()==2){
				try { 
					//DECGL -> <VARG> <DECGL'>
					VARG varg = (VARG)prod.get(1);
					DECGL decgl = (DECGL)prod.get(0);
					accionSemantica(varg, decgl);
				} catch (ClassCastException e) {
					//DECGL -> <CONSTS> <DECGL'>					
					try {
						CONSTS consts = (CONSTS)prod.get(1);
						DECGL decgl;
						decgl = (DECGL)prod.get(0);
						accionSemantica(consts,decgl);
					} catch (ClassCastException e1) {
						throw new AlgorithmicError("Error en MOSTRAR");
					}					
				}
			}else{
				throw new AlgorithmicError("Error algoritmico en la produccion DECL", "La cantidad de elementos es erronea");
			}
		}
		return true;
	}

	
	private void accionSemantica(VARG varg, DECGL decgl) throws SemanticException {
		this.lista=decgl.lista;
		this.lista.addAll(0,varg.lista);	
	}
	

	private void accionSemantica(CONSTS consts, DECGL decgl) throws SemanticException {
		this.lista=decgl.lista;
		this.lista.addAll(0,consts.lista);
	}
	
	public ArrayList<HojaDecl> getLista(){
		return this.lista;
	}
	

}
