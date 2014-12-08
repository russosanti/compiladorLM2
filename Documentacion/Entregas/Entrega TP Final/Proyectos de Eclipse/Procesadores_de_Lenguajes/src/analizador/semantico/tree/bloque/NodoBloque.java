package analizador.semantico.tree.bloque;

import java.util.ArrayList;
import java.util.Iterator;

import exceptions.CodeException;
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
	
	public void generateCode() throws CodeException {
		
		Iterator<NodoProc> it = this.hijos.iterator();
		while(it.hasNext()){
			it.next().generateDecl();
		}
		
		it = this.hijos.iterator();
		while(it.hasNext()){
			it.next().generateCode();
		}
	}

}
