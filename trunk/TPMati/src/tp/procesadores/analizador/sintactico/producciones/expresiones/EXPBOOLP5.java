package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.expresiones.MenorNaturales;
import tp.procesadores.analizador.semantico.arbol.expresiones.NodoExpresion;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class EXPBOOLP5 extends ProduccionC {


	public EXPBOOLP5(){
		SimboloTerminal igual = new SimboloTerminal("<<");
		producciones.add(igual);
		EXP0 exp = null;
		producciones.add(exp);
	}
	
	//EXPBOOL' ->  << EXP
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r; 
//		System.out.println("EXPBOOLP5");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r )
		{ 
			ArbolHandler arbolSp = new ArbolHandler();
			producciones.set(1, new EXP0()); 
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, new NodoExpresion(), arbolSp, tablaH);
			MenorNaturales menor = new MenorNaturales(arbolH, arbolSp.getArbol());
			arbolS.setArbol(menor);
		}
		return r; 
	}

}
