package tabladesimbolos;

import java.util.ArrayList;
import java.util.Iterator;

import tabladesimbolos.declaraciones.DeclFuncProc;
import tabladesimbolos.declaraciones.DeclFuncProcDesconocidas;
import tabladesimbolos.declaraciones.Declaraciones;
import analizador.lexico.tokens.ID;
import analizador.lexico.tokens.TokenTypes;
import exceptions.SemanticException;
import exceptions.TableException;
import exceptions.TypeException;

/** Interfaz para la Tabla de Simbolos
 * @author Santy */
public interface iTablaDeSimbolos {
	
	public boolean existeIDGlobal(String s);
	public boolean declaredID(String contexto,String s) throws TableException;
	public boolean declaredID(String s) throws TableException;
	public boolean declaredFunc(String s);
	public void setErrorMode();
	public boolean isErrorMode();
	
	public TokenTypes searchPalabraReservada(String palabra);
	public void setContexto(String context);
	public String getContexto();
	public void setContextoGlobal();
	public boolean isGlobalContext();
	public boolean hasMain();
	public boolean checkParam(String id,ArrayList<Tipo> params)throws TypeException;
	
	public Tipo getTipoFuncProc(String id) throws TableException;
	public Tipo getTipoGlobal(String id) throws TableException;
	public Tipo getTipo(String contexto,String id) throws TableException;
	public Tipo getTipo(String id) throws TableException;
	
	public DeclFuncProc getFuncProc(String id) throws TableException;
	public Declaraciones getGlobal(String id) throws TableException;
	public Declaraciones get(String contexto,String id) throws TableException;
	public Declaraciones get(String id) throws TableException;
	
	//variables globales, constantes globales y arreglos (pueden ser solo globales)
	public boolean addVarGlobales(String id, Tipo tipo)throws SemanticException ;
	public boolean addConstGlobales(String id, Tipo tipo, String valor) throws SemanticException;
	public boolean addArray(String id, Tipo tipo, int tamanio)throws SemanticException;
	public boolean addFuncGlobales(String id, ArrayList<Tipo> param, Tipo retorno)throws SemanticException;
	public boolean addProcGlobales(String id, ArrayList<Tipo> param)throws SemanticException;
	public boolean addFuncProcDesconocidas(String id, ArrayList<Tipo> tipo, ID idtoken);
	public boolean addFuncProcDesconocidas(String id, ArrayList<Tipo> tipo, ID idtoken, Tipo retorno);
	
	//idem con contexto
	public boolean addVar(String id, Tipo tipo)throws SemanticException;
	public boolean addVar(String id, Tipo tipo, String contexto)throws SemanticException;
	public boolean addParam(String id, Tipo tipo, boolean byref)throws SemanticException, TableException;
	public boolean addParam(String id, Tipo tipo, boolean byref ,String contexto)throws SemanticException, TableException;
	public boolean addConst(String id, Tipo tipo, String valor)throws SemanticException;
	public boolean addConst(String id, Tipo tipo, String valor, String contexto)throws SemanticException;
	public boolean addFunc(String id, ArrayList<Tipo> param, Tipo retorno)throws SemanticException;
	public boolean addFunc(String id, ArrayList<Tipo> param, Tipo retorno, String contexto)throws SemanticException;
	public boolean addProc(String id, ArrayList<Tipo> param)throws SemanticException;
	public boolean addProc(String id, ArrayList<Tipo> param, String contexto)throws SemanticException;
	
	public Tipo getTipoPendiente();
	public void setTipoPendiente(Tipo tipoPendiente);
	public Iterator<DeclFuncProcDesconocidas> getIteratorDesconocidas();
	
	public void printTabla();
	
}
