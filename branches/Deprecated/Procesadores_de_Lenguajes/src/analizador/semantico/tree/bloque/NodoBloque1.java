package analizador.semantico.tree.bloque;

import java.util.ArrayList;
import analizador.semantico.tree.Nodo;

public class NodoBloque1 extends Nodo<Sentencias>{
	
	
	public NodoBloque1(ArrayList<Sentencias> t){
		super(t);
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOBLOQUE1;
	}
}
