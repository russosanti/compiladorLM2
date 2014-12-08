package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.expresiones.NodoExpresion;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class FACTP1 extends ProduccionC {

	public FACTP1(){
		SimboloTerminal corchete1 = new SimboloTerminal("[");
		producciones.add(corchete1);
		EXP0 exp = null; 
		producciones.add(exp);
		SimboloTerminal corchete2 = new SimboloTerminal("]");
		producciones.add(corchete2);
	}
	
	//FACT'.ArbolS = EXP.ArbolS
	
	//FACT'	->	[EXP]
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r; 
//		System.out.println("FACTP1");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			ArbolHandler arbolSp = new ArbolHandler(); 
			NodoExpresion expresion = new NodoExpresion();
			producciones.set(1, new EXP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, new ClaseNodo(), arbolSp, tablaH);
			expresion.add(arbolSp.getArbol());
			arbolH.add(expresion);
			arbolS.setArbol(arbolH);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if (!r)
				{
					merrores.mostrarYSkipearError("Se espera palabra cierre corchete ']'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
			}
		}
		return r;
	}
}
