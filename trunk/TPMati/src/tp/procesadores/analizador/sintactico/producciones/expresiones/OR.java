package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.expresiones.FuncionOr;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PalabraReservada;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;
import tp.procesadores.analizador.sintactico.producciones.SimboloTerminal;

public class OR extends ProduccionC {

	
	public OR(){
		PalabraReservada and = new PalabraReservada("or"); 
		producciones.add(and);
		SimboloTerminal parentesis1 = new SimboloTerminal("("); 
		producciones.add(parentesis1);
		EXPBOOL0 exp1 = null; 
		producciones.add(exp1);
		SimboloTerminal comma = new SimboloTerminal(","); 
		producciones.add(comma);
		EXPBOOL0 exp2 = null; 
		producciones.add(exp2);
		SimboloTerminal parentesis2 = new SimboloTerminal(")"); 
		producciones.add(parentesis2);
	}
	
	//OR.ArbolS = CrearNodoOr ( EXPBOOL1.ArbolS, EXPBOOL2.ArbolS )
	
	//OR	 	 	->   or(EXPBOOL,EXPBOOL)
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) 
	{
//		System.out.println("OR");
		boolean r; 
		r = producciones.get(0).reconocer(lexic, visitor, sintactic); 
		if ( r )
		{
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			if ( r )
			{
				ArbolHandler arbolSp1 = new ArbolHandler(); 
				producciones.set(2, new EXPBOOL0());
				r = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
				if ( r )
				{ 
					r = producciones.get(3).reconocer(lexic, visitor, sintactic);
					if ( r )
					{
						ArbolHandler arbolSp2 = new ArbolHandler(); 
						producciones.set(4, new EXPBOOL0());
						r = producciones.get(4).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
						if ( r )
						{ 
							r = producciones.get(5).reconocer(lexic, visitor, sintactic);
							arbolS.setArbol(new FuncionOr(arbolSp1.getArbol(), arbolSp2.getArbol()));
							if ( !r )
							{ 
								merrores.mostrarYSkipearError("Se espera parentesis ')'", lexic, sintactic, visitor);
								sintactic.setEstadoAnalisis(false);
								r = true;
							}
						}
					}else
					{
						merrores.mostrarYSkipearError("Se espera coma ',' en funcion 'or'", lexic, sintactic, visitor);
						sintactic.setEstadoAnalisis(false);
						r = true;
					}
					
				}
			}else
			{
				merrores.mostrarYSkipearError("Se espera parentesis '('", lexic, sintactic, visitor);
				sintactic.setEstadoAnalisis(false);
				r = true;
			}
		}
		return r;
	}
}
