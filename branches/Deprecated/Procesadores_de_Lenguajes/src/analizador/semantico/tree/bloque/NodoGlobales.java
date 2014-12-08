package analizador.semantico.tree.bloque;

import java.util.ArrayList;

import analizador.semantico.tree.Nodo;
import analizador.semantico.tree.decl.HojaDecl;

public class NodoGlobales extends Nodo<HojaDecl>{
	
	public NodoGlobales(){
		super();
	}
	
	public NodoGlobales(ArrayList<HojaDecl> t){
		super(t);
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOGLOBALES;
	}
}
