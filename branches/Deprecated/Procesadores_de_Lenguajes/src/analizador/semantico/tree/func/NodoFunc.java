package analizador.semantico.tree.func;

import tabladesimbolos.Tipo;
import analizador.semantico.tree.bloque.NodoBloque1;
import analizador.semantico.tree.decl.NodoDecl;
import analizador.semantico.tree.exp.NodoExp;

public class NodoFunc extends NodoProc{

	private Tipo tipoRetorno;
	private NodoExp retorno;
	
	public NodoFunc(String id, NodoParam param, NodoDecl decl,NodoBloque1 bloque, Tipo tipo,NodoExp retorno) {
		super(id, param, decl, bloque);
		this.tipoRetorno = tipo;
		this.retorno = retorno;
	}

	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODOFUNC;
	}
}
