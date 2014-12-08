package analizador.semantico.reglas.bloque;


import java.util.Stack;
import exceptions.AlgorithmicError;
import exceptions.SemanticException;
import exceptions.TypeException;
import tabladesimbolos.Tipo;
import analizador.lexico.tokens.Token;
import analizador.lexico.tokens.TokenTypes;
import analizador.semantico.reglas.ReglaSemantica;
import analizador.semantico.reglas.exp.EXP;
import analizador.semantico.tree.exp.NodoExp;
import analizador.sintactico.estructuras.LRApilable;
import analizador.sintactico.estructuras.NoTerminal;

public class IDAUX extends NoTerminal implements ReglaSemantica{
	
	private boolean isArray;
	private NodoExp indice;
	
	public IDAUX(){
		super(IDAUX.class.getSimpleName());
		this.isArray = false;
	}
	
	public IDAUX(String s){
		super(s);
		this.isArray = false;
	}
	
	public boolean accionSemantica(Stack<LRApilable> argumentos)throws SemanticException{
		if(argumentos == null || argumentos.size() == 0){
			return this.accionSemantica();
		}else{
			if(argumentos.size() == 3){
				try{
					Token corchApertura = (Token)argumentos.elementAt(2);
					Token corchCierre = (Token)argumentos.elementAt(0);
					if(corchApertura.getType() != TokenTypes.CORCHETE_APERTURA || corchCierre.getType() != TokenTypes.CORCHETE_CIERRE){
						throw new AlgorithmicError("Error en el IDAUX","La Exprecion del indice de un arreglo debe estar rodeada por []");
					}
					return this.accionSemantica((EXP)argumentos.elementAt(1));
				}catch(Exception e){
					throw new AlgorithmicError("Error en el IDAUX",e.getMessage()); //semantic error
				}
			}else{
				throw new AlgorithmicError("Error en el IDAUX"); //semantic error
			}
		}
	}
	
	private boolean accionSemantica(){
		this.isArray = false;
		return true;
	}
	
	private boolean accionSemantica(EXP exp) throws TypeException{
		if(exp.getTipo() == Tipo.INTEGER){
			this.indice = exp.getArbol();
			this.isArray = true;
			return true;
		}else{
			throw new TypeException("El tipo debe ser un entero");
		}
		
	}

	public boolean isArray() {
		return isArray;
	}

	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}

	public NodoExp getIndice() {
		return indice;
	}

	public void setIndice(NodoExp indice) {
		this.indice = indice;
	}
	
	
	
}
