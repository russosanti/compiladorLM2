package analizador.semantico.tree.bloque;

import java.util.ArrayList;

import analizador.semantico.tree.Nodo;
import analizador.semantico.tree.decl.HojaDecl;

public class NodoGlobales extends Nodo<HojaDecl>{
	
	public NodoGlobales(){
		super();
		this.tipoNodo = TipoNodo.NODOGLOBALES;
	}
	
	public NodoGlobales(ArrayList<HojaDecl> t){
		super(t);
		this.tipoNodo = TipoNodo.NODOGLOBALES;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOGLOBALES;
	}
}
