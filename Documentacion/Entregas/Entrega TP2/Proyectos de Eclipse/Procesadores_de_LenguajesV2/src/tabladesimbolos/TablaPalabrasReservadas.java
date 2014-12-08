package tabladesimbolos;

import java.util.Arrays;
import java.util.List;

public class TablaPalabrasReservadas {
	private List<String> lista_reservadas;
	private List<String> lista_tipos;
	
	public TablaPalabrasReservadas(){
		this.lista_reservadas = Arrays.asList("and","begin","byref","byval","const","do",
				"else","end-func","end-if","end-proc","end-while","false","function","if",
				"not","or","procedure","read","show","showln","then","true","var",
				"while");
		this.lista_tipos = Arrays.asList("boolean","integer");
	}
	
	public boolean existPalabraReservada(String palabra){
		return this.lista_reservadas.contains(palabra);
	}
	
	public boolean existTipo(String palabra){
		return this.lista_tipos.contains(palabra);
	}
}
