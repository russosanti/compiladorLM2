package analizador.semantico.tree;

import analizador.semantico.tree.bloque.NodoBloque;
import analizador.semantico.tree.bloque.NodoGlobales;

public class NodoS implements INodo{
	
	private NodoGlobales glob;
	private NodoBloque bloque;
	
	public NodoS(NodoGlobales g, NodoBloque b){
		this.glob = g;
		this.bloque =b;
	}

	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.S;
	}
}
