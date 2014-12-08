package analizador.sintactico.estructuras;

import interfaz.UI;

import java.util.ArrayList;
import java.util.HashMap;

import analizador.lexico.tokens.TokenTypes;
import analizador.sintactico.helpers.ExcelReader;


/** Clase para manejar la tabla SLR del algoritmo de parseo Ascendente
 * @author Santy */
public class Tabla implements ITabla {
	/** Tabla que contiene las filas en forma de HashMap */
	private ArrayList<HashMap<String,TableAction>> tablaAccion;
	private ArrayList<HashMap<String,TableAction>> tablaIrA;
	
	/** @param s Direccion desde donde se carga la tabla*/
	public Tabla(String s){
		this.tablaAccion = new ArrayList<HashMap<String,TableAction>>();
		this.tablaIrA = new ArrayList<HashMap<String,TableAction>>();
		if(!this.cargarTabla(s)){
			if(!this.cargarTabla2(s)){
				UI.error("Error cargando la Tabla desde el Excel");
				System.exit(1);
			}
		}
	}
	
	/** Busca la accion para un determinado estado y terminal */
	public TableAction findAction(int estado, String term){
		term = term.toUpperCase();
		return this.tablaAccion.get(estado).get(term);
	}
	
	/** Busca la accion para un determinado estado y no terminal */
	public TableAction findGoto(int estado, String term){
		term = term.toUpperCase();
		return this.tablaIrA.get(estado).get(term);
	}
	
	/** Busca la accion para un determinado estado y terminal/no terminal.
	 * De haber un terminal y un no terminal con el mismo nombre agarra el terminal*/
	public TableAction find(int estado, String term){
		TableAction acc = this.tablaAccion.get(estado).get(term);
		if(acc==null){
			acc = this.tablaIrA.get(estado).get(term);
		}
		return acc;
	}
	
	/** Busca la accion para un determinado estado y terminal / no terminal
	 * 	Si se pone true busca en los terminales, sino en los no terminales*/
	public TableAction find(int estado, String term, boolean b){
		if(b){
			return this.tablaAccion.get(estado).get(term);
		}else{
			return this.tablaIrA.get(estado).get(term);
		}
	}
	
	/** Cargador de la tabla. El sheet de la tabla se debe llamar Tabla, Table, SLR Table o Tabla SLR
	 * @param s Path del .xls
	 * @return True si lo puede cargar, sino false */
	private boolean cargarTabla(String s){
		ExcelReader er = new ExcelReader(s);
		if(!er.operSheet("Tabla"))
			if(!er.operSheet("Table"))
				if(!er.operSheet("SLR Table"))
					if(!er.operSheet("Tabla SLR")){
						return false;
					}
		
		ArrayList<String> nombres = er.readRow(0);
		boolean error = false;
		int i = 1;
		int cut = nombres.indexOf(TokenTypes.EOF.toString());
		while(i<=er.filas()-1 && !error){
			HashMap<String,TableAction> fila = new HashMap<String,TableAction>();
			for(int j=1;j<=cut;j++){
				TableAction t = new TableAction(er.readCell(j,i));
				fila.put(nombres.get(j), t);
			}
			if(!tablaAccion.add(fila)){
				error = true;
			}
			fila = new HashMap<String,TableAction>();
			for(int j=cut+1;j<=er.columnas()-1;j++){
				TableAction t = new TableAction(er.readCell(j,i));
				fila.put(nombres.get(j), t);
			}
			if(!tablaIrA.add(fila)){
				error = true;
			}
			i++;
		}
		return !error;
	}
	
	/** Cargador de la tabla. El sheet de la tabla se debe llamar Tabla, Table, SLR Table o Tabla SLR
	 * @param s Path del .xls
	 * @return True si lo puede cargar, sino false */
	private boolean cargarTabla2(String s){
		ExcelReader er = new ExcelReader(s);
		if(!er.operSheet("Accion"))
			if(!er.operSheet("Action"))
				return false;
		
		ArrayList<String> nombres = er.readRow(0);
		boolean error = false;
		int i = 1;
		while(i<=er.filas()-1 && !error){
			HashMap<String,TableAction> fila = new HashMap<String,TableAction>();
			for(int j=1;j<=er.columnas()-1;j++){
				TableAction t = new TableAction(er.readCell(j,i));
				fila.put(nombres.get(j), t);
			}
			if(!tablaAccion.add(fila)){
				error = true;
			}
			i++;
		}
		
		if(!er.operSheet("Ir A"))
			if(!er.operSheet("Goto"))
				return false;
		
		nombres = er.readRow(0);
		i = 1;
		while(i<=er.filas()-1 && !error){
			HashMap<String,TableAction> fila = new HashMap<String,TableAction>();
			for(int j=1;j<=er.columnas()-1;j++){
				TableAction t = new TableAction(er.readCell(j,i));
				fila.put(nombres.get(j), t);
			}
			if(!tablaIrA.add(fila)){
				error = true;
			}
			i++;
		}
		
		return !error;
	}
}
