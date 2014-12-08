package analizador.semantico.reglas.decl;

import java.util.ArrayList;
import java.util.Stack;

import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.tree.decl.HojaDecl;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;
import exceptions.AlgorithmicError;

public class DECL extends NoTerminal implements ReglaSemantica{
	
	private ArrayList<HojaDecl> lista;
	
	public DECL() {
		super(DECL.class.getSimpleName());
	}	
	public DECL(String decl) {
		super(decl);
	}
	
	public boolean accionSemantica(Stack<LRApilable> prod){
		
		if (prod== null || prod.size()==0){
			this.lista = new ArrayList<HojaDecl>();
		}else{
			if (prod.size()==2){
				try { 
					//DECL -> <VARS> <DECL'>
					VARS vars = (VARS)prod.get(1);
					DECL decl = (DECL)prod.get(0);
					accionSemantica(vars, decl);
				} catch (ClassCastException e) {
					//DECL -> <CONSTS> <DECL'>					
					try {
						CONSTS consts = (CONSTS)prod.get(1);
						DECL decl;
						decl = (DECL)prod.get(0);
						accionSemantica(consts,decl);
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
	
	private void accionSemantica(CONSTS consts, DECL decl) {
		this.lista=decl.lista;
		this.lista.addAll(0,consts.lista);		
	}
	
	private void accionSemantica(VARS vars, DECL decl) {
		this.lista=decl.lista;
		this.lista.addAll(0,vars.lista);		
	}
	
	public ArrayList<HojaDecl> getLista(){
		return this.lista;
	}

}
