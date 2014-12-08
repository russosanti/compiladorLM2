package analizador.lexico.tokens;

public enum TokenTypes {
	NUMERO(1), //Enteros
	CADENA(2), //Strings
	SYMBOLO(3), //Ya no se usa. Se deja para posibles extensiones
	EOF(4),	//Fin de Archivo
	ID(5),
	ENDLINE(6), //;
	BOOLEANO(7), //true o false
	PALABRARESERVADA(8), //ya no se usa
	CORCHETE_APERTURA(9), // [
	CORCHETE_CIERRE(10), // ]
	PARENTESIS_APERTURA(11), // (
	PARENTESIS_CIERRE(12), // )
	TIPO(13),
	OPERADOR_BOOLEANO(14), // = <>
	OPERADOR_BOOLEANO_N(15), // > < >= <=
	SUMA_RESTA(16), // + -
	MULT_DIV(17), // * /
	ASIGNACION(18), // :=
	//Arranco los tipos para las palabras reservadas
	AND(19),
	OR(20),//para las palabras logicas
	BEGIN(21),
	BY(22), //para el pasaje de parametros byval o byref
	CONST(23),  //indica si es constante o variable
	DO(24),
	ELSE(25),
	END_FUNC(26),
	END_IF(27),
	END_PROC(28),
	END_WHILE(29),
	FUNCTION(30),
	IF(31),
	CONST_IGUAL(32), // =
	NOT(33),
	PROCEDURE(34),
	READ(35),
	SHOW(36), //aplicapara SHOW y SHOWLN
	THEN(37),
	VAR(38),
	WHILE(39),
	DEF_TIPO(40), //esto es para el :
	COMMA(41), // ,
	ERROR(-1);

	private int codigo;
	
	private TokenTypes(int x){
		this.codigo = x;
	}
	
	public int codigo(){
		return this.codigo;
	}
			
}
