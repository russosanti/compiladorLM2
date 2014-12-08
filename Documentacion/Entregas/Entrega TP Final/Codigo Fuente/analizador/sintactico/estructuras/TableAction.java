package analizador.sintactico.estructuras;

import interfaz.UI;

/** Clase para manejar las celdas de la Tabla SLR
 * @author Santy */
public class TableAction {
	/** Tipo de Accion definido en el Enum  */
	private ActionType type;
	/** Paso siguiente */
	private int paso;
	
	/** Constructor a partir de un tipo y el paso
	 * @param t Tipo de la accion
	 * @param p paso a realizar*/
	public TableAction(ActionType t, int p){
		this.type = t;
		this.paso = p;
	}
	
	/** Constructor a partir de un String y elige la accion automaticamente
	 * @param t String de la accion a realizar
	 * @param p paso a realizar */
	public TableAction(String t, int p){
		t = t.trim();
		this.type = ActionType.toActionType(t);
		this.paso = p;
	}
	
	/** Se le pasa el valor de la celda estilo JFLAP y lo separa automaticamente
	 * @param s valor de la celda */
	public TableAction(String s){
		this.paso = -1;
		if(s == null){
			this.type = ActionType.ERROR;
		}else{
			s = s.trim();
			if(s.length()==0 || s.equals("")){
			this.type = ActionType.ERROR;
			}else{
				if(s.equals("acc")){
					this.type = ActionType.ACCEPT;
				}else{
					if(isNumber(s)){
						this.type = ActionType.GOTO;
						this.paso = Integer.parseInt(s); //ya chequeo que sea numero en isNumber
					}else{
						String aux = s.substring(0,1);
						s = s.substring(1,s.length());
						s = s.trim();
						if(isNumber(s)){
							this.paso = Integer.parseInt(s);
							if(aux.equals("s")){
								this.type = ActionType.SYNTHETISE;
							}else{
								if(aux.equals("r")){
									this.type = ActionType.REDUCE;
								}else{
									UI.error("Error cargando la tabla: ","Accion no reconocida, solo de aceptan r (para Reduce) y s (para Synthetise)");
									System.exit(1);
								}
							}
						}else{
							UI.error("Error cargando la tabla: ","Un Synthetise o Reduce no tiene numero o hay un campo mal formado");
							System.exit(1);
						}
					}
				}
			}
		}
	}
	
	/** Enum para los tipos de acciones
	 * @author Santy */
	public enum ActionType{
		SYNTHETISE,
		REDUCE,
		GOTO,
		ACCEPT,
		ERROR;
		
		/** Convierte el string a la accion correspondiente siguiendo las reglas de la tabla JFLAP
		 * @param s Campo de la accion de la tabla
		 * @return El ActionType o null si hay un error */
		public static ActionType toActionType(String s){
			s = s.toLowerCase();
			s = s.trim();
			switch(s){
				case "s":
					return SYNTHETISE;
				case "r":
					return REDUCE;
				case "":
					return ERROR;
				case "acc":
					return ACCEPT;
				case " ":
					return ERROR;
				default:
					if(isNumber(s)){
						return GOTO;
					}
					return null;
			}
		}
	}
	
	/** Retorna el tipo
	 * @return Tipo de accion */
	public ActionType getType() {
		return type;
	}
	 /** Retorna el paso que se debe realizar luego 
	  * @return Paso a realizar */
	public int getPaso() {
		return paso;
	}
	
	/** Devuelve true si es un numero, false si no lo es
	 * @param c Char para detectar 
	 * @return true si es, sino false */
	public static boolean isNumber(char c){
		if(c == '0' ||
			c == '1' ||
			c == '2' ||
			c == '3' ||
			c == '4' ||
			c == '5' ||
			c == '6' ||
			c == '7' ||
			c == '8' ||
			c == '9')
			return true;
		else
			return false;
			
	}
	 /** Devuelve true si es un numero, false si no lo es
	  * @param s String a checkear
	  * @return True si es numero, sino false */
	public static boolean isNumber(String s){
		try{
			Integer.parseInt(s);
		}catch(Exception e){
			return false;
		}
			return true;		
	}
	
	/** Convierte la accion a string */
	public String toString(){
		return "Tipo Accion: " + this.type + " Paso: " +this.paso;
	}
}
