package analizador.semantico.tree.bloque;
import exceptions.CodeException;
import analizador.semantico.tree.INodo;

public interface Mostrable extends INodo{
		
	public String toString();
	
	public void showable() throws CodeException;

	public void printTree();

}
