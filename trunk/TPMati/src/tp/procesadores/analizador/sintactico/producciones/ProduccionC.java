
package tp.procesadores.analizador.sintactico.producciones;

import java.util.ArrayList;
import java.util.List;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.semantico.arbol.ArbolHandler;
import tp.procesadores.analizador.semantico.arbol.ArbolUtils;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ElementoIdentificador;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LConstHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.LVarHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaConstantes;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaParametrosHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ListaVariables;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Metodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.MetodoHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Parametro;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.ParametroHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TSHandler;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.manejador.errores.ManejadorErrores;

public class ProduccionC implements ProduccionI{ 

	public List<ProduccionI> producciones = new ArrayList<ProduccionI>();
	public ManejadorErrores merrores = new ManejadorErrores();
	public ArbolUtils arbolUtils = new ArbolUtils();
	
	@Override
	public void add(ProduccionI simbolo) {
		producciones.add(simbolo);
	}

	@Override
	public void remove(ProduccionI simbolo) {
		producciones.remove(simbolo);
	}

	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) {
		return false;
	}

	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolSp1) {
		return false;
	}

	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic, ListaConstantes listaH,
			LConstHandler listaS) {
		return false;
	}

	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic, ElementoIdentificador elemento) {
		return false;
	}

	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic, TablaDeSimbolos tablaH,
			TSHandler tablaS) {
		return false;
	}

	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic, ListaVariables listaH,
			LVarHandler listaS) {
		return false;
	}

	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic, TSHandler lista) {
		return false;
	}

	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
			TSHandler tablaS) {
		return false;
	}

	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
			TablaDeSimbolos tablaH) {
		return false;
	}

	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic, ClaseNodo arbolH, ArbolHandler arbolS,
			TablaDeSimbolos tablaH, TSHandler tablaS) {
		return false;
	}

	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic, Metodo metodoH, MetodoHandler metodoS) {
		return false;
	}

	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic, List<Parametro> parametrosH,
			ListaParametrosHandler parametrosS) {
		return false;
	}

	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic, Parametro parametro,
			ParametroHandler parametroSp1) {
		return false;
	}
}
