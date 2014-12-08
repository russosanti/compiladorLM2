package tp.procesadores.compilador.generadorcodigo;



public class GeneradorCodigoUtils {


	public String generarCodigoSuma(String label, String op1, String op2, String tab) 
	{
        StringBuilder strbldr = new StringBuilder();
        strbldr.append(generarMov(tab+"AX", op1)).append("\n");
        strbldr.append(String.format(tab+"ADD\t%s, %s", "AX", op2)).append("\n");
        strbldr.append(generarMov(tab+label, "AX")).append("\n");
        return strbldr.toString();
	}

   public String generarMultiplicacionEntera(String label, String op1, String op2, String tab)
   {
       StringBuilder strbldr = new StringBuilder();
       strbldr.append(tab+generarMovHaciaBx(op1)).append("\n");
       strbldr.append(tab+generarMovHaciaAx(op2)).append("\n");
       strbldr.append(tab+String.format("IMUL\t%s","BX")).append("\n");
       strbldr.append(tab+generarMovDesdeAx(label)).append("\n");
       return strbldr.toString();
   }

   
   public String generarMultiplicacionNatural(String label, String op1, String op2, String tab)
   {
       StringBuilder strbldr = new StringBuilder();
       strbldr.append(tab+generarMovHaciaBx(op1)).append("\n");
       strbldr.append(tab+generarMovHaciaAx(op2)).append("\n");
       strbldr.append(tab+String.format("MUL\t%s","BX")).append("\n");
       strbldr.append(tab+generarMovDesdeAx(label)).append("\n");
       return strbldr.toString();
   }

   
   public String generarDivisionEntera(String label, String op1, String op2, String tab)
   {
       StringBuilder strbldr = new StringBuilder();
       strbldr.append(tab+generarMovHaciaAx(op1)).append("\n");
       strbldr.append(tab+generarMovHaciaBx(op2)).append("\n");
       strbldr.append("CMP\tBX, 0").append("\n");
       strbldr.append("JNE\t EVITARINTERRUPCION").append("\n");
       strbldr.append("int\t00h").append("\n");
       strbldr.append("EVITARINTERRUPCION:").append("\n");
       strbldr.append(tab+String.format("IDIV\t%s", "BX")).append("\n");
       strbldr.append(tab+generarMov(label, "AX")).append("\n");
       return strbldr.toString();
   }

   public String generarDivisionNatural(String label, String op1, String op2, String tab)
   {
       StringBuilder strbldr = new StringBuilder();
       strbldr.append(tab+generarMovHaciaAx(op1)).append("\n");
       strbldr.append(tab+generarMovHaciaBx(op2)).append("\n");
       strbldr.append("CMP\tBX, 0").append("\n");
       strbldr.append("JNE\t EVITARINTERRUPCION").append("\n");
       strbldr.append("int\t00h").append("\n");
       strbldr.append("EVITARINTERRUPCION:").append("\n");
       strbldr.append(tab+String.format("DIV\t%s", "BX")).append("\n");
       strbldr.append(tab+generarMov(label, "AX")).append("\n");
       return strbldr.toString();
   }

   
   public String generarResta(String label, String op1, String op2, String tab)
   {
       StringBuilder strbldr = new StringBuilder();
       strbldr.append(tab+generarMov("AX", op1)).append("\n");
       strbldr.append(tab+String.format("SUB\t%s, %s", "AX", op2)).append("\n");
       strbldr.append(tab+generarMov(label, "AX")).append("\n");
       return strbldr.toString();
   }
   
   
   public String generarExprBool(String op1, String op2, String tab)
   {
       StringBuilder sb = new StringBuilder();
       sb.append(tab+generarMov("AX", op1)).append("\n");
       sb.append(tab+generarCmp("AX", op2)).append("\n");
       return sb.toString();
   }

   private static String generarCmp(String destino, String desde)
   {
       StringBuilder strbldr = new StringBuilder();
       strbldr.append("CMP\t").append(destino).append(", ").append(desde);
       return strbldr.toString();
   }

   public String generarJumpIncondicional(String labelDestino, String tab)
   {
       StringBuilder strbldr = new StringBuilder();
       strbldr.append(tab+"JMP\t").append(labelDestino);
       return strbldr.toString();
   }
   
   public static String generarMovHaciaAx(String desde)
   {
       return generarMov("AX", desde);
   }
   
   public static String generarMovDesdeAx(String hacia)
   {
       return generarMov(hacia, "AX");
   }
   
   public static String generarMovHaciaBx(String desde)
   {
       return generarMov("BX", desde);
   }
   
   public static String generarMov(String destino, String desde)
   {
       StringBuilder strbldr = new StringBuilder();
       strbldr.append("MOV\t").append(destino).append(", ").append(desde);
       return strbldr.toString();
   }
   
   public String generarCodigoInicial()
   {
       StringBuilder strbldr = new StringBuilder();
       //codigo obligatorio de ejecucion
       strbldr.append(" SEG Segment\n");
       strbldr.append(" ASSUME CS:SEG,SS:SEG,DS:SEG,ES:SEG\n");
       strbldr.append(" ORG 0100h\n");
       return strbldr.toString();
   }
   

   public String generarEsPar(String labelVariable)
   {
       StringBuilder strbldr = new StringBuilder();
       strbldr.append(generarMov("Ax", labelVariable));
       strbldr.append(String.format("CMP\t Ax, 0001h"));
       return strbldr.toString();
   }

   
   public String generarFinPrograma(String labelPrincipal){
	   StringBuilder strbldr = new StringBuilder();
	   strbldr.append("FINAL:").append("\n");
	   strbldr.append("POP\taritmetico\n");
	   strbldr.append("   ").append(generarMov("AH", "4ch")).append("\n");
	   strbldr.append("   ").append(String.format("INT\t%s", "21h")).append("\n");
	   strbldr.append("END\t"+labelPrincipal).append("\n\n\n");
	   return strbldr.toString(); 
   }
   
   public String generarInicioProcedimiento(String label)
   {
       StringBuilder strbldr = new StringBuilder();
       strbldr.append(label).append(" PROC NEAR\n");
       return strbldr.toString();
   }
   
   public String generarFinProcedimiento(String label,String valor)
   {
       StringBuilder strbldr = new StringBuilder();
       if (!valor.equals("0")){
    	   strbldr.append(generarMov("SP", "BP")).append("\n");
    	   strbldr.append("POP\tBP").append("\n");
           strbldr.append(" RET ").append(valor).append("\n");
       }else
           strbldr.append(" RET\n");
       strbldr.append(label).append(" ENDP\n");
       return strbldr.toString();
   }

	public String generarParametroPorRef(String registro, int indice) 
	{
		StringBuilder strbldr = new StringBuilder();
		strbldr.append(generarMov(registro, "[BP + " + indice + "]")).append("\n");
		return strbldr.toString();
	}

	public String generarEncabezadoParametrosPorReferencia() {
		StringBuilder strbldr = new StringBuilder();
		strbldr.append("PUSH\tBP").append("\n");
		strbldr.append(generarMov("BP", "SP")).append("\n");
		return strbldr.toString();
	}

	public String generarParametroPorVal(String registro) 
	{
		
		return null;
	}

}
