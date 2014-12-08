package tp.procesadores.analizador.sintactico.producciones.expresiones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.NUMERO;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class PASAJE2 extends ProduccionC{

	public PASAJE2(){
		NUMERO numero = new NUMERO();  
		producciones.add(numero);
		PASAJEP0 pasajep = null;
		producciones.add(pasajep);
	}
	
	//PASAJE'.ArbolH = NUMERO.ArbolS
	//PASAJE.ArbolS = PASAJE'.ArbolS
	
	//PASAJE -> NUMERO PASAJE'
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic, 
			ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH) {
		boolean r; 
		ArbolHandler arbolSp1 = new ArbolHandler();
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, new ClaseNodo(), arbolSp1);
		arbolH.add(arbolSp1.getArbol());
		if ( r ){
			ArbolHandler arbolSp2 = new ArbolHandler();
			producciones.set(1, new PASAJEP0());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolH, arbolSp2, tablaH);
			arbolS.setArbol(arbolSp2.getArbol());
		}
		return r;
	}
}
