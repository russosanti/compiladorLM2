package analizador.sintactico.estructuras;


/** Interfaz para el manejador de la gramatica
 * @author Santy */
public interface IGramatica {
		
		/** Devuelve la produccion numero num
		 * @param num Numero de la produccion a devolver
		 * @return La produccion numero num o null si no la encuentra */
		public Produccion getProduction(int num);
		
		/** Muestra la gramatica cargada */
		public void showGrammar();
}
