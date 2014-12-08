package analizador.sintactico.estructuras;

/** Interfaz para la Tabla SLR
 * @author Santy */
public interface ITabla {
	/** Buscador en la tabla
	 * @param estado Estado por el cual buscar
	 * @param term Terminal o no Terminal por el cual buscar
	 * @return La accion a realizar */
	/** Busca la accion para un determinado estado y terminal */
	public TableAction findAction(int estado, String term);
	
	/** Busca la accion para un determinado estado y no terminal */
	public TableAction findGoto(int estado, String term);
	
	/** Busca la accion para un determinado estado y terminal/no terminal.
	 * De haber un terminal y un no terminal con el mismo nombre agarra el terminal*/
	public TableAction find(int estado, String term);
	
	/** Busca la accion para un determinado estado y terminal / no terminal
	 * 	Si se pone true busca en los terminales, sino en los no terminales*/
	public TableAction find(int estado, String term, boolean b);
}
