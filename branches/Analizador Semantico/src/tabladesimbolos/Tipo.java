package tabladesimbolos;

public enum Tipo{
	INTEGER, BOOLEAN, STRING, VOID;
	
	public static Tipo parseTipo(String s){
		if(s.equalsIgnoreCase("integer")){
			return INTEGER;
		}else{
			if(s.equalsIgnoreCase("boolean")){
				return BOOLEAN;
			}else{
				if(s.equalsIgnoreCase("string")){
					return STRING;
				}else{
					return STRING;
				}
			}
		}
	}
}
