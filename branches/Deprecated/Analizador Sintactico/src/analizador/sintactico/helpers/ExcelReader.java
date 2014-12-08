package analizador.sintactico.helpers;

import interfaz.UI;
import jxl.*; 

import java.io.*;
import java.util.ArrayList;

/**Clase para leer de un Excel
 * @author Santy */
public class ExcelReader {
	 /** Archivo del Excel */
	private Workbook Excel;
	/** Hoja de la que se esta leyendo */
	private Sheet sh;
	
	/** Constructor a traves de un path, usa la sheet 0 por default
	 * @param path Direccion al Archivo .xls*/
	public ExcelReader(String path){
		try{
			this.Excel = Workbook.getWorkbook(new File(path));
			this.sh = this.Excel.getSheet(0); // por default se cae en la primera
		}catch(Exception e){
			UI.error("Error abriendo el archivo de la tabla SLR: "+path,e.getMessage());
			System.exit(1);
		}
	}
	
	/** Constructor a traves del path y pone sheet como activa
	 * @param path Direccion al archivo .xls
	 * @param sheet Sheet activa a usar
	 */
	public ExcelReader(String path, String sheet){
		try{
			this.Excel = Workbook.getWorkbook(new File(path));
			this.sh = this.Excel.getSheet(sheet); // por default se cae en la primera
		}catch(Exception e){
			UI.error("Error abriendo el archivo de la tabla SLR: "+path,e.getMessage());
			System.exit(1);
		}
	}
	
	/** Constructor a partir de un File y pone le sheet 0 como activa
	 * @param f Archivo del .xls*/
	public ExcelReader(File f){
		try{
			this.Excel = Workbook.getWorkbook(f);
			this.sh = this.Excel.getSheet(0);
		}catch(Exception e){
			UI.error("Error abriendo el archivo de la tabla SLR: "+f.getPath(),e.getMessage());
			System.exit(1);
		}
	}
	
	/** Devuelve si existe o no un sheet con es Nombre
	 * @param s Nombre de la hoja
	 * @return True si existe, sino false. */
	public boolean sheetExists(String s){
		if(this.Excel.getSheet(s) == null)
			return false;
		else
			return true;
	}
	
	/** Abre y pone como activa una hoja con es nombre. Si falla devuelve false y queda activa la anterior
	 * @param sheet Hoja a activar
	 * @return True si lo logra, false sino  */
	public boolean operSheet(String sheet){
		Sheet sh = this.Excel.getSheet(sheet);
		if(sh==null){
			return false;
		}
		this.sh = sh;
		return true;
	}
	
	/** Devuelve la cantidad de columnas que se tienen. Ojo arranca de 0 el sheet
	 * @return Cantidad de columnas */
	public int columnas(){
		return this.sh.getColumns();
	}
	
	/** Devuelve la cantidad de filas que se tienen. Ojo arranca de 0 el sheet
	 * @return Cantidad de filas */
	public int filas(){
		return this.sh.getRows();
	}
	
	/** Lee una celda especifica
	 * @param c Nro columna
	 * @param f Nro de Fila
	 * @return String del contenido */
	public String readCell(int c, int f){
		try{
			return this.sh.getCell(c,f).getContents();
		}catch(Exception e){
			UI.error("Error leyendo la celda: "+c + " ; "+f + " del archivo Excel", e.getMessage());
			return null;
		}
	}
	
	/** Lee una fila entera
	 * @param r Nro de Fila a leer
	 * @return ArrayList de Strings */
	public ArrayList<String> readRow(int r){
		try{
			ArrayList<String> arr = new ArrayList<String>();
			for(int i=0;i<=this.columnas()-1;i++){
				arr.add(this.sh.getCell(i,r).getContents());
				
			}
			return arr;
		}catch(Exception e){
			UI.error("Error leyendo la Fila: "+r + " del archivo Excel", e.getMessage());
			return null;
		}
	}
	
	/** Lee una Columna entera
	 * @param c Nro de Columna a leer
	 * @return ArrayList de Strings */
	public ArrayList<String> readColumn(int c){
		try{
			ArrayList<String> arr = new ArrayList<String>();
			for(int i=0;i<=this.filas()-1;i++){
				arr.add(this.sh.getCell(c,i).getContents());
			}
			return arr;
		}catch(Exception e){
			UI.error("Error leyendo la Fila: "+c + " del archivo Excel", e.getMessage());
			return null;
		}
	}
	
	/** Muestra los valores del Excel */
	public void showSheet(){ 	
		int numColumnas = this.sh.getColumns(); 
		int numFilas = this.sh.getRows(); 
		String data;  
		for(int fila = 0; fila < numFilas; fila++) { // Recorre cada fila de la hoja
			for (int columna = 0; columna < numColumnas; columna++) { // Recorre cada columna de la fila 						
						data = this.sh.getCell(columna, fila).getContents(); 
						System.out.print(data + " "); 
			} 
			System.out.println("\n"); 
		} 
	}
}
