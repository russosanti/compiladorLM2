package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class VARSPP2 extends ProduccionC
{
	public VARSPP2 ()
	{
		PalabraReservada puntoycomma = new PalabraReservada(";");
		producciones.add(puntoycomma);
	}

	//VARS�' -> ;
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic,
			ListaVariables listaH, LVarHandler listaS) 
	{		
		boolean r;
//		System.out.println("VARSPP2");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		listaS.setLista(listaH);
		return r;
	}

}
