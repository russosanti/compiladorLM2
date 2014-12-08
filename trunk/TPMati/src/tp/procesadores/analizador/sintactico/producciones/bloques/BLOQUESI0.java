package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.expresiones.NodoExpresionBooleana;
import tp.procesadores.analizador.semantico.arbol.principal.Bloque;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.expresiones.EXPBOOL0;

public class BLOQUESI0 extends ProduccionC {

	public BLOQUESI0()
	{
		PalabraReservada si = new PalabraReservada("si");
		producciones.add(si);
		EXPBOOL0 expbool = null;
		producciones.add(expbool);
		PalabraReservada entonces = new PalabraReservada("entonces");
		producciones.add(entonces);
		BLOQUE0 bloque = null;
		producciones.add(bloque);
		BLOQUESIP0 bloquesip = null;
		producciones.add(bloquesip);
	}

	//BLOQUESI ->   si EXPBLOQUE entonces BLOQUE BLOQUESI'
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r;
//		System.out.println("BLOQUESI0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			NodoExpresionBooleana expresion = new NodoExpresionBooleana();
			ArbolHandler arbolSp1 = new ArbolHandler(); 
			producciones.set(1, new EXPBOOL0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, new ClaseNodo(), arbolSp1, tablaH);
			expresion.add(arbolSp1.getArbol());
			arbolH.add(expresion);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if ( r )
				{
					ArbolHandler arbolSp2 = new ArbolHandler();
					producciones.set(3, new BLOQUE0());
					r = producciones.get(3).reconocer(lexic, visitor, sintactic, new Bloque(), arbolSp2, tablaH);
					arbolH.add(arbolSp2.getArbol());
					if ( r )
					{
						ArbolHandler arbolSp3 = new ArbolHandler();
						producciones.set(4, new BLOQUESIP0());
						r = producciones.get(4).reconocer(lexic, visitor, sintactic, arbolH, arbolSp3, tablaH);
						arbolS.setArbol(arbolSp3.getArbol());
					}
				}
				else
				{
					merrores.mostrarYSkipearError("Se espera palabra reservada 'entonces'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
				
			}
			
		}
		return r;
	}
}