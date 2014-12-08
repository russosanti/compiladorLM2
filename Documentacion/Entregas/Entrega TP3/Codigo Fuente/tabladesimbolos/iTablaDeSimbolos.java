package tabladesimbolos;

import java.util.ArrayList;

import tabladesimbolos.declaraciones.DeclFuncProc;
import tabladesimbolos.declaraciones.Declaraciones;
import exceptions.SemanticException;
import exceptions.TableException;
import exceptions.TypeException;
import analizador.lexico.tokens.TokenTypes;

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
	public String getConexto();
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
	
	public void printTabla();
	
	public String getIdRecur();
	public void setIdRecur(String idRecur);
	public Tipo getRetornoRecur();
	public void setRetornoRecur(Tipo retornoRecur);
	public ArrayList<Tipo> getParamRecur();
	public void setParamRecur(ArrayList<Tipo> paramRecur);
	public boolean isFuncRecur();
	public void setFuncRecur(boolean isFuncRecur);
	public void resetRecur();
}
