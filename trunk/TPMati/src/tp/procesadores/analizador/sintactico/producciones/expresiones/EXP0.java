package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class EXP0 extends ProduccionC {

	public EXP0()
	{
		TERM0 term = new TERM0();
		EXPP0 expp = new EXPP0();
		this.add(term); 
		this.add(expp); 
	}
	
	//EXP'.ArbolH = TERM.ArbolS 
	//EXP.ArbolS = EXP'.ArbolS
	
	//EXP -> TERM EXP'
	//@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r; 
//		System.out.println("EXPO"); 
		ArbolHandler arbolSp = new ArbolHandler();
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp, tablaH);
		if (r) {
			ArbolHandler arbolSp2 = new ArbolHandler();
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolSp.getArbol(), arbolSp2, tablaH);
			arbolS.setArbol(arbolSp2.getArbol());
		}
		return r; 
	}
} 
