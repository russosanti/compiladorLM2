package analizador.semantico.tree.bloque;

import java.util.ArrayList;

import analizador.semantico.tree.Nodo;
import analizador.semantico.tree.func.NodoProc;

public class NodoBloque extends Nodo<NodoProc>{
	
	public NodoBloque(){
		super();
	}
	
	public NodoBloque(ArrayList<NodoProc> t){
		super(t);
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOBLOQUE;
	}
}
