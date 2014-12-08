package analizador.semantico.tree.bloque;

import analizador.lexico.tokens.Cadena;
import analizador.semantico.tree.exp.Hoja;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class HojaString extends Hoja implements Mostrable{ //esto lo hago para poder asignarlos
	
	private String valorString; 
	
	public HojaString(Cadena cadena){
		super(cadena);
		this.valorString = cadena.getLexema();
		this.tipoNodo = TipoNodo.HOJAINTEGER;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
		}catch(Exception e){
			
		}
	}
	
	public String toString(){
		return "Nombre: " +this.tipoNodo + " Valor: " + this.valorString;
	}
}
