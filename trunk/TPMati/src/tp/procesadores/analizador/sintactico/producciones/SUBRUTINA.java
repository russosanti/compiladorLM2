package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TSHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.subrutinas.FP0;

public class SUBRUTINA extends ProduccionC {

	public SUBRUTINA() {
		FP0 fp = null;
		producciones.add(fp);
		SUBRUTINA subrutina = null;
		producciones.add(subrutina);
	}
	
	//tablaHija = new TablaSimbolos()
	//tablaHija.SetPadre ( DECLARACIONES.TablaSimbolosH ) 
	//FP.TablaH = tablaHija

	//FP.ArbolH = SUBRUTINA.ArbolH 
	//SUBRUTINA1.ArbolH = FP.ArbolS
	//SUBRUTINA.ArbolS = SUBRUTINA1.ArbolS

	// SUBRUTINA -> FP SUBRUTINA | e
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS, TablaDeSimbolos tablaH, TSHandler tablaS) {
		boolean r;
//		System.out.println("SUBRUTINA");
		
		ArbolHandler arbolSp1 = new ArbolHandler();
		TSHandler tablaSp1 = new TSHandler();
		producciones.set(0, new FP0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic, arbolH, arbolSp1, tablaH, tablaSp1);
		if (r) 
		{
			ArbolHandler arbolSp2 = new ArbolHandler();
			TSHandler tablaSp2 = new TSHandler();
			producciones.set(1, new SUBRUTINA());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic, arbolSp1.getArbol(), arbolSp2, tablaH, tablaSp2);
			arbolS.setArbol(arbolSp2.getArbol());
			tablaS.setTabla(tablaSp2.getTabla());
		}else 
		{
			arbolS.setArbol(arbolH);
			tablaS.setTabla(tablaH);
			r = true;
		}
		return r;
	}
}
