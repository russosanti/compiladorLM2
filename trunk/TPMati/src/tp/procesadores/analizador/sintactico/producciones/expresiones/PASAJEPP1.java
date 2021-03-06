package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.PALABRA;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class PASAJEPP1 extends ProduccionC {

	public PASAJEPP1(){
		PALABRA palabra = new PALABRA();
		producciones.add(palabra);
		PASAJEP0 pasaje = new PASAJEP0();
		producciones.add(pasaje);
	}

	//PASAJE'.ArbolH = PALABRA.ArbolS
	//PASAJE''.ArbolS = PASAJE'.ArbolS
	
	//PASAJE'' ->   PALABRA PASAJE'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH){
		boolean r; 
		
		ArbolHandler arbolSp1 = new ArbolHandler();
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, new ClaseNodo(), arbolSp1, tablaH);
		arbolH.add(arbolSp1.getArbol());
		if ( r ){
			ArbolHandler arbolSp2 = new ArbolHandler();
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
			arbolS.setArbol(arbolSp2.getArbol());
		}
		return r; 
	}
	

}
