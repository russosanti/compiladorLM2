package analizador.semantico.reglas;

import java.util.Stack;
import exceptions.SemanticException;
import analizador.sintactico.estructuras.LRApilable;

public interface ReglaSemantica {
	
	public boolean accionSemantica(Stack<LRApilable> prod) throws SemanticException;
}
