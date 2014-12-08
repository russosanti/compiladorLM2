package analizador.semantico.reglas.bloque;

import java.util.ArrayList;
import java.util.Stack;

import analizador.lexico.tokens.Cadena;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.reglas.exp.EXP;
import analizador.semantico.tree.bloque.HojaString;
import analizador.semantico.tree.bloque.Mostrable;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;
import exceptions.AlgorithmicError;

public class MOSTRAR extends NoTerminal implements ReglaSemantica {
	
	protected ArrayList<Mostrable> lista;
	
	public MOSTRAR(){
		super(MOSTRAR.class.getSimpleName());
	}
	
	public MOSTRAR(String s){
		super(s);
	}

	@Override
	public boolean accionSemantica(Stack<LRApilable> prod) {
		if (prod==null || prod.size()==0){
			this.lista = new ArrayList<Mostrable>();
		}else{
			if (prod.size()==2){				
				try { 
					//MOSTRAR -> CADENA <MOSTRAR1>
					Cadena cadena = (Cadena)prod.get(1);
					MOSTRAR1 mostrar1 = (MOSTRAR1)prod.get(0);
					accionSemantica(cadena, mostrar1);
				} catch (ClassCastException e) {
					//MOSTRAR -> <EXP> <MOSTRAR1>					
					try {
						EXP exp = (EXP)prod.get(1);
						MOSTRAR1 mostrar1;
						mostrar1 = (MOSTRAR1)prod.get(0);
						accionSemantica(exp,mostrar1);
					} catch (ClassCastException e1) {
						throw new AlgorithmicError("Error en MOSTRAR");
					}					
				}
			}			
		}
		return true;
	}

	private void accionSemantica(EXP exp, MOSTRAR1 mostrar1) {
		mostrar1.lista.add(0,exp.getArbol());
		this.lista=mostrar1.lista;
	}

	private void accionSemantica(Cadena cadena, MOSTRAR1 mostrar1) {
		mostrar1.lista.add(0,new HojaString(cadena));
		this.lista=mostrar1.lista;
	}

	public ArrayList<Mostrable> getLista() {
		return lista;
	}

	public void setLista(ArrayList<Mostrable> lista) {
		this.lista = lista;
	}
	
	
}
