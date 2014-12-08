package analizador.semantico.tree.decl;

import tabladesimbolos.Tipo;

public class HojaVar extends HojaDecl {
	
	public HojaVar(String s, Tipo t) {
		super(s, t);
	}

	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.HOJAVAR;
	}

}
