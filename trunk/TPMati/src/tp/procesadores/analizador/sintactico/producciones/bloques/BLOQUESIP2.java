package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.principal.Bloque;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class BLOQUESIP2 extends ProduccionC {

	public BLOQUESIP2()
	{
		PalabraReservada sino = new PalabraReservada("sino");
		producciones.add(sino);
		BLOQUE0 bloque = null;
		producciones.add(bloque);
		PalabraReservada finsi2 = new PalabraReservada("fin-si");
		producciones.add(finsi2);
		SimboloTerminal pyc = new SimboloTerminal(";");
		producciones.add(pyc);
	}

	//BLOQUESIP0 -> sino BLOQUE fin-si;
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
		boolean r;
//		System.out.println("BLOQUESIP2");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			ArbolHandler arbolSp1 = new ArbolHandler();
			producciones.set(1, new BLOQUE0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, new Bloque(), arbolSp1, tablaH);
			arbolH.add(arbolSp1.getArbol());
			arbolS.setArbol(arbolH);
			if ( r )
			{
				r = producciones.get(2).reconocer(lexic, visitor, sintactic);
				if ( r )
				{
					r = producciones.get(3).reconocer(lexic, visitor, sintactic);
					if( !r )
					{
						merrores.mostrarYSkipearError("Se punto y coma ';'", lexic, sintactic, visitor);
						sintactic.setEstadoAnalisis(false);
						r = true;
					}
				}else
				{
					merrores.mostrarYSkipearError("Se espera palabra reservada 'fin-si'", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true;
				}
			}
			
		}
		return r;
	}
}