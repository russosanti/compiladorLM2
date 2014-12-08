package analizador.semantico.tree.bloque;

import java.util.ArrayList;

import analizador.semantico.tree.Nodo;
import analizador.semantico.tree.func.NodoProc;

public class NodoBloque extends Nodo<NodoProc>{
	
	public NodoBloque(){
		super();
		this.tipoNodo = TipoNodo.NODOBLOQUE;
	}
	
	public NodoBloque(ArrayList<NodoProc> t){
		super(t);
		this.tipoNodo = TipoNodo.NODOBLOQUE;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOBLOQUE;
	}
}
