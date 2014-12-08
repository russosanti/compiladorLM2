package tp.procesadores.analizador.sintactico.producciones.parametrizaciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class TIPOPARAM2 extends ProduccionC 
{
	public TIPOPARAM2()
	{
		SimboloTerminal val = new SimboloTerminal("val");
		producciones.add(val);
	}

	//	val
	
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) 
	{
		boolean r;
//		System.out.println("TIPOPARAM2");
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		
		return r;
	}
}
