package analizador.semantico.tree.decl;

import exceptions.CodeException;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;
import tabladesimbolos.Tipo;

public class HojaArray extends HojaVar{

	private int tamano;
	
	public HojaArray(String s, Tipo t,int tam) {
		super(s, t);
		this.tamano = tam;
	}

	public int getTamano() {
		return tamano;
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.HOJAVAR;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" ID: "+this.getId()+" Tipo: "+this.getTipo()+" Tamano: "+this.getTamano();
	}
	
	@Override
	public void generateCode() throws CodeException {
		ITree cg = ExecutionTree.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append(this.getId()+" DW ");
		if(this.tamano>0){
			sb.append("0");
			for (int i=1;i<= this.tamano-1;i++){
				sb.append(",0");
			}
			cg.insertCode(new String(sb));
		}else{
			throw new CodeException("Error definiendo un Arreglo","El tamanio debe ser mayor a 0");
		}
		
	}
}
