package tp.procesadores.analizador.sintactico.producciones.bloques;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.NodeVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class FPOASIG0 extends ProduccionC {

	public FPOASIG0()
	{
		FPOASIG1 fpoasig1 = null; 
		producciones.add(fpoasig1);
		FPOASIG2 fpoasig2 = null;
		producciones.add(fpoasig2);
		FPOASIG3 fpoasig3 = null;
		producciones.add(fpoasig3);
	}

	/**
	 * FPOASIG ->	(PASAJE); |
					:= EXP; | 
					[EXP] := EXP;
	 */
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic,
			ClaseNodo arbolH, ArbolHandler arbolS,TablaDeSimbolos tablaH) 
	{
		boolean r;
//		System.out.println("FPOASIG0");
		if ( sintactic.siguiente.accept(visitor).equals("("))
		{
			NodeVisitor identVisitor = new NodeVisitor();
			if (!tablaH.existeMetodo(arbolH.accept(identVisitor)) ) {
				merrores.mostrarErrorSemantico("El metodo \'"+ arbolH.accept(identVisitor) + "\' NO esta declarado", sintactic);
				sintactic.setEstadoAnalisis(false);
			}
			ArbolHandler arbolSp1 = new ArbolHandler();	
			producciones.set(0, new FPOASIG1());
			r = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH);
			arbolS.setArbol(arbolSp1.getArbol());
		}
		else
		{
			if ( sintactic.siguiente.accept(visitor).equals(":="))
			{
				NodeVisitor identVisitor = new NodeVisitor();
				if(!tablaH.existeId(arbolH.accept(identVisitor))){ 
					if (!tablaH.esParametroDelContexto(arbolH.accept(identVisitor)))
					{
						merrores.mostrarErrorSemantico("El identificador \'"+ arbolH.accept(identVisitor) + "\' NO esta declarado", sintactic);
						sintactic.setEstadoAnalisis(false);
					}
				}else{
					if(!tablaH.esVariable(arbolH.accept(identVisitor))){
						merrores.mostrarErrorSemantico("No se puede realizar una asignación a una constante: \'"+ arbolH.accept(identVisitor) + "\'", sintactic);
						sintactic.setEstadoAnalisis(false);
					}
				}
				ArbolHandler arbolSp2 = new ArbolHandler();	
				producciones.set(1, new FPOASIG2());
				r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
				arbolS.setArbol(arbolSp2.getArbol());
			}
			else
			{
				if (sintactic.siguiente.accept(visitor).equals("["))
				{ 
					ArbolHandler arbolSp3 = new ArbolHandler();	
					producciones.set(2, new FPOASIG3());
					r = producciones.get(2).reconocer(lexic, visitor, sintactic, arbolH, arbolSp3, tablaH);
					arbolS.setArbol(arbolSp3.getArbol());
				}
				else
				{
					merrores.mostrarYSkipearError("Se espera alguno de los siguientes operadores {'(',':=','['}", lexic, sintactic, visitor);
					sintactic.setEstadoAnalisis(false);
					r = true; 
				}
			}
		}
		return r;
	}
}