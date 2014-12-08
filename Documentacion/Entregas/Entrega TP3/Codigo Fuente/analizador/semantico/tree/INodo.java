package analizador.semantico.tree;

public interface INodo {

	public enum TipoNodo{
		S,
		EXP,
		NOT,
		PASAJE,
		ACCESOARRAY,
		ACCESO,
		LLAMADA,
		NODOEXPBOOL,
		HOJAINTEGER,
		HOJABOOLEAN,
		HOJAVAR,
		HOJAARRAY,
		HOJACONST,
		HOJAPARAM,
		NODOREAD,
		NODOSHOW,
		NODOWHILE,
		NODOIF,
		ASIGACCESSO,
		ASIGARRAY,
		NODOPROC,
		NODOFUNC,
		NODOPARAM,
		NODOBLOQUE,
		NODOBLOQUE1,
		NODODECL,
		NODOGLOBALES;
	}
	
	public void printTree();
	
	public TipoNodo getTipoNodo();

}
