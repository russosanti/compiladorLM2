package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class VARSP0 extends ProduccionC
{
	public VARSP0 ()
	{
		PALABRA palabra = new PALABRA();
		producciones.add(palabra);
		TVAR0 tvar = null;
		producciones.add(tvar);
	}
	
	//PALABRA.ListaVariablesH = VARS'.ListaVariablesH
	//TVAR.ListaVariablesH = PALABRA.ListaVariablesS 
	//VARS'.ListVariablesS = TVAR.ListaVariablesS 
	
	//VARS' -> PALABRA TVAR
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ListaVariables listaH, LVarHandler listaS) 
	{
		boolean r; 
//		System.out.println("VARSP0");
		LVarHandler listaSp1 = new LVarHandler();
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, listaH, listaSp1);
		if ( r ) 
		{
			LVarHandler listaSp2 = new LVarHandler();
			producciones.set(1, new TVAR0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, listaSp1.getLista(), listaSp2);
			listaS.setLista(listaSp2.getLista());
		}
		else
		{
			merrores.mostrarYSkipearError("Se esperaba un identificador", lexic, sintactic, visitor);
			sintactic.setEstadoAnalisis(false);
			r = true;
		}
		return r; 
	}
}
