package tp.procesadores.analizador.sintactico.producciones.constantesyvariables;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LConstHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaConstantes;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;

public class CONSTS0 extends ProduccionC
{
	public CONSTS0 ()
	{
		PalabraReservada constante = new PalabraReservada("const");
		producciones.add(constante);
		CONSTSP0 constsp = null;
		producciones.add(constsp);
	}
	
	//CONSTS'.ListaConstanteslH = CONSTS.ListaConstantesH 
	//CONSTS.ListaConstantesS = CONSTS'.ListaConstantesS  
	
	//CONSTS -> const CONSTS'
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic,
			ListaConstantes listaH, LConstHandler listaS) 
	{
		boolean r; 
//		System.out.println("CONSTS0");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if ( r ) 
		{
			LConstHandler listaSp = new LConstHandler();
			producciones.set(1, new CONSTSP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, listaH, listaSp);
			listaS.setLista(listaSp.getLista());
		}
		return r; 	
	}
}
