package analizador.semantico.tree.decl;

import java.util.ArrayList;
import java.util.Iterator;

import exceptions.CodeException;
import analizador.semantico.tree.INodo;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoDecl implements INodo{
	
	private ArrayList<HojaDecl> lista;
	
	
	public NodoDecl(ArrayList<HojaDecl> lis){
		this.lista = lis;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.NODODECL;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			Iterator<HojaDecl> it = this.lista.iterator();
			prin.ident();
			while(it.hasNext()){
				it.next().printTree();
			}
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo();
	}

	@Override
	public void generateCode() throws CodeException {
		ITree cg = ExecutionTree.getInstance();
		cg.insertCode("mov DX, sp; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada");
		Iterator<HojaDecl> it = this.lista.iterator();
		while(it.hasNext()){
			it.next().generateCode();
		}
		cg.insertCode("mov sp, DX; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada");
	}

}
