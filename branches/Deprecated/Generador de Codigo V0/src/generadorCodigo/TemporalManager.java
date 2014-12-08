package generadorCodigo;

import tabladesimbolos.SingleTabla;
import tabladesimbolos.iTablaDeSimbolos;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class TemporalManager {
	
	private int counter = 0;
	private int max = 0;
	private static TemporalManager instance;
	
	public static TemporalManager getInstance(){
		if(instance == null){
			instance = new TemporalManager();
		}
		return instance;
	}
	
	/** Te retorna el temporal y te olvidas de la declaracion
	 * @return */
	public String getTemporal(){
		this.counter++;
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		String tempName;
		if(tabla.isGlobalContext()){
			tempName = "temp"+this.counter;
		}else{
			tempName = "temp"+this.counter+tabla.getContexto();
		}
		if(this.counter > max){
			ITree cg = ExecutionTree.getInstance();
			cg.insertCode("MOV CX,sp ;Para evitar el corrimiento");
			cg.insertCode("MOV BX, 0 ;Sino se comporta muy extrano");
			cg.insertCode("MOV DX, AX ;Sino se comporta muy extrano");
			cg.insertCode(tempName + " DW 0");
			cg.insertCode("MOV AX, 0 ;Arrastra basura la declaracion!!");
			cg.insertCode("MOV AL, 0 ;Arrastra basura la declaracion!!");
			cg.insertCode("MOV AH, 0 ;Arrastra basura la declaracion!!");
			cg.insertCode("MOV AX, DX ;Sino se comporta muy extrano");
			cg.insertCode("MOV sp,CX ;Recupero el corrimiento");
			this.max++;
		}
		return tempName;
	}
	
	public void useTemp(){
		this.counter--;
	}
	
	public String getLastTemporal(){
		if(this.counter==0){
			return null;
		}
		
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		if(tabla.isGlobalContext()){
			return "temp"+this.counter;
		}else{
			return "temp"+this.counter+tabla.getContexto();
		}
	}
	
	public void resetTemporales(){
		this.counter = 0;
		this.max = 0;
	}
	
	public int getCounter(){
		return this.counter;
	}
}
