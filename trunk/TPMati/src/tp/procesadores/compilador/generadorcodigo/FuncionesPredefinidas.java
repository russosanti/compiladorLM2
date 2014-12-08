package tp.procesadores.compilador.generadorcodigo;


public class FuncionesPredefinidas {

	public String aEntero()
	{		
		StringBuilder str = new StringBuilder();
        str.append(generarInicioProcedimiento("AENTERO")).append("\n");
        str.append(generarMov("AX","BX")).append("\n");
		str.append(generarFinProcedimiento("AENTERO", "")).append("\n");
		return str.toString();		
	}			
	
	public String aNatural()
	{
		StringBuilder str = new StringBuilder();
        str.append(generarInicioProcedimiento("ANATURAL")).append("\n");
		str.append("MOV\t"+"AX,"+"BX"+"\n");
		str.append("CMP\t"+"AX,AX"+"\n");
		str.append("JS\t"+"NEGATIVO"+"\n");
		str.append("RET"+"\n");
		str.append("NEGATIVO:"+"\n");
		str.append("MOV\t"+"BX,-1"+"\n");
		str.append("IMUL\t"+"BX"+"\n");
		str.append(generarMov("AX","BX")).append("\n");
		str.append(generarFinProcedimiento("ANATURAL", "")).append("\n");
		return str.toString();
	}	
	
	 public String generarProcedimientosWrite()
	 {
        StringBuilder strBldr = new StringBuilder();
        strBldr.append(generarProcedimientoWriteStr()).append("\n");
        strBldr.append(generarProcedimientoWriteCtr()).append("\n");
        strBldr.append(generarProcedimientoWriteNum()).append("\n");
        return strBldr.toString();
    }
	 
    public String generarProcedimientoWriteStr()
    { 
        StringBuilder strBldr = new StringBuilder();
        strBldr.append(generarComentario("***Comienzo rutina writeSTR***")).append("\n");
        strBldr.append(generarComentario("Imprime por pantalla un string sin <Enter> al final")).append("\n");
        strBldr.append(generarComentario("Parametros:")).append("\n");
        strBldr.append(generarComentario("Parametro entrada 1: direccion de memoria donde comienza el string a imprimir (word/via push del llamador)")).append("\n");
        strBldr.append(generarComentario("Parametro entrada 2: cantidad de caracteres del string (word/via push del llamador)")).append("\n");
        strBldr.append(generarInicioProcedimiento("writeSTR")).append("\n");
        strBldr.append(generarPush("bp")).append("\n");
        strBldr.append(generarMov("bp","sp")).append("\n");
        strBldr.append(generarPush("ax")).append("\n");
        strBldr.append(generarPush("bx")).append("\n");
        strBldr.append(generarPush("cx")).append("\n");
        strBldr.append(generarPush("si")).append("\n");
        strBldr.append(generarComentario("a SI se le asigna el primer parametro (direccion)")).append("\n");
        strBldr.append(generarMov("si","[bp+6]")).append("\n");
        strBldr.append(generarComentario("a CX se le asigna el segundo parametro (cantidad de caracteres)")).append("\n");
        strBldr.append(generarMov("cx","[bp+4]")).append("\n");
        strBldr.append("xor bx,bx").append("\n");
        strBldr.append(generarLabel("loop")).append("\n");
        strBldr.append(generarMov("al","[si]")).append("\n");
        strBldr.append(generarMov("ah","0Eh")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append("inc bx").append("\n");
        strBldr.append("inc si").append("\n");
        strBldr.append(generarCmp("bx","cx")).append("\n");
        strBldr.append("jne loop").append("\n");
        strBldr.append(generarPop("si")).append("\n");
        strBldr.append(generarPop("cx")).append("\n");
        strBldr.append(generarPop("bx")).append("\n");
        strBldr.append(generarPop("ax")).append("\n");
        strBldr.append(generarPop("bp")).append("\n");
        strBldr.append(generarFinProcedimiento("writeSTR","4")).append("\n");
        strBldr.append(generarComentario("***Fin rutina writeSTR***")).append("\n");
        return strBldr.toString();
    }
    
    
    private static String generarProcedimientoWriteCtr()
    {
        StringBuilder strBldr = new StringBuilder();
        strBldr.append(generarComentario("***Comienzo rutina writeCRLF***")).append("\n");
        strBldr.append(generarComentario("Imprime por pantalla un caracter <Enter> (<CR><LF>)")).append("\n");
        strBldr.append(generarComentario("Parametros: -")).append("\n");
        strBldr.append(generarInicioProcedimiento("writeCRLF")).append("\n");
        strBldr.append(generarPush("AX")).append("\n");
        strBldr.append(generarMov("al","0Dh")).append("\n");
        strBldr.append(generarMov("ah","0Eh")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append(generarMov("al","0Ah")).append("\n");
        strBldr.append(generarMov("ah","0Eh")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append(generarPop("AX")).append("\n");
        strBldr.append(generarFinProcedimiento("writeCRLF","")).append("\n");
        strBldr.append(generarComentario("***Fin rutina writeCRLF***")).append("\n");
        return strBldr.toString();
    }
    private static String generarProcedimientoWriteNum()
    {
        StringBuilder strBldr = new StringBuilder();
        strBldr.append(generarComentario("***Comienzo rutina writeNUM***")).append("\n");
        strBldr.append(generarComentario("Imprime por pantalla un numero (word con signo) sin <Enter> al final")).append("\n");
        strBldr.append(generarComentario("Parametros:")).append("\n");
        strBldr.append(generarComentario("Parametro entrada 1: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador)")).append("\n");
        strBldr.append(generarComentario("Parametro entrada 2: numero a imprimir (word/via push del llamador)")).append("\n");
        strBldr.append(generarInicioProcedimiento("writeNUM")).append("\n");
        strBldr.append(generarPush("bp")).append("\n");
        strBldr.append(generarMov("bp", "sp")).append("\n");
        strBldr.append(generarComentario("variable local/ [bp-1] = signo (00h=positivo, 01h=negativo)")).append("\n");
        strBldr.append("sub sp,1").append("\n");
        strBldr.append(generarComentario("variable local/ [bp-7] = espacio para imprimir (db 6 dup(?))")).append("\n");
        strBldr.append("sub sp,6").append("\n");
        strBldr.append(generarPush("ax")).append("\n");
        strBldr.append(generarPush("bx")).append("\n");
        strBldr.append(generarPush("cx")).append("\n");
        strBldr.append(generarPush("dx")).append("\n");
        strBldr.append(generarPush("si")).append("\n");
        strBldr.append(generarMov("[bp-1]", "00h")).append("\n");
        strBldr.append(generarMov("ax", "[bp+4]")).append("\n");
        strBldr.append(generarCmp("[bp+6]", "0")).append("\n");
        strBldr.append(generarComentario("Si no es aritmetica con signo, comienza")).append("\n");
        strBldr.append("je comenzar").append("\n");
        strBldr.append(generarCmp("ax", "0")).append("\n");
        strBldr.append(generarComentario("Si no es negativo, comienza")).append("\n");
        strBldr.append("jge comenzar").append("\n");
        strBldr.append(generarComentario("Realiza ax = -ax")).append("\n");
        strBldr.append("neg ax").append("\n");
        strBldr.append(generarMov("[bp-1]", "01h")).append("\n");
        strBldr.append(generarLabel("comenzar")).append("\n");
        strBldr.append(generarMov("bx", "10")).append("\n");
        strBldr.append(generarMov("cx", "0")).append("\n");
        strBldr.append(generarMov("si", "bp")).append("\n");
        strBldr.append("sub si,8").append("\n");
        strBldr.append(generarLabel("proxdiv")).append("\n");
        strBldr.append("dec si").append("\n");
        strBldr.append("xor dx,dx").append("\n");
        strBldr.append("div bx").append("\n");
        strBldr.append("add dl,48").append("\n");
        strBldr.append(generarMov("[si]", "dl")).append("\n");
        strBldr.append("inc cx").append("\n");
        strBldr.append(generarCmp("ax", "0")).append("\n");
        strBldr.append("jnz proxdiv").append("\n");
        strBldr.append(generarCmp("[bp-1]", "00h")).append("\n");
        strBldr.append("jz mostrar").append("\n");
        strBldr.append("dec si").append("\n");
        strBldr.append(generarMov("[si]", "'-'")).append("\n");
        strBldr.append("inc cx").append("\n");
        strBldr.append(generarLabel("mostrar")).append("\n");
        strBldr.append(generarPush("si")).append("\n");
        strBldr.append(generarPush("cx")).append("\n");
        strBldr.append(generarCall("writeSTR")).append("\n");
        strBldr.append(generarPop("si")).append("\n");
        strBldr.append(generarPop("dx")).append("\n");
        strBldr.append(generarPop("cx")).append("\n");
        strBldr.append(generarPop("bx")).append("\n");
        strBldr.append(generarPop("ax")).append("\n");
        strBldr.append(generarMov("sp", "bp")).append("\n");
        strBldr.append(generarPop("bp")).append("\n");
        strBldr.append(generarFinProcedimiento("writeNUM","4")).append("\n");
        strBldr.append(generarComentario("***Fin rutina writeNUM***")).append("\n");
        return strBldr.toString();
    }
    public String generarProcedimientoRead()
    {
        StringBuilder strBldr = new StringBuilder();
        strBldr.append(generarComentario("***Comienzo rutina readln***")).append("\n");
        strBldr.append(generarComentario("Obtiene por teclado un numero (word con o sin signo)")).append("\n");
        strBldr.append(generarComentario("(usa rutina writeSTR, notar que posee dos macros)")).append("\n");
        strBldr.append(generarComentario("Solamente permite ingresar caracteres <0>..<9>, <->, <Backspace>, <Enter> (cuando corresponden)")).append("\n");
        strBldr.append(generarComentario("No realiza control de overflow, ni permite <Control><Break> para cortar la ejecucion del programa")).append("\n");
        strBldr.append(generarComentario("Parametros:"));
        strBldr.append(generarComentario("Parametro entrada: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador)")).append("\n");
        strBldr.append(generarComentario("Parametro salida: numero obtenido (word/via pop del llamador)")).append("\n");
        strBldr.append(generarComentario("(el llamador antes debera efectuar un push de un word para que esta rutina deje el resultado ahi)")).append("\n");
        strBldr.append("dig macro digbase").append("\n");
        strBldr.append(generarCmp("al","digbase")).append("\n");
        strBldr.append("jl inicioread").append("\n");
        strBldr.append(generarCmp("al","'9'")).append("\n");
        strBldr.append("jg inicioread").append("\n");
        strBldr.append(generarMov("ah","0Eh")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append(generarMov("[bp-1]","03h")).append("\n");
        strBldr.append(generarMov("cl","al")).append("\n");
        strBldr.append("sub cl,48").append("\n");
        strBldr.append(generarMov("ax","si")).append("\n");
        strBldr.append(generarMov("bx","000Ah")).append("\n");
        strBldr.append(generarComentario("AX = AX * 10")).append("\n");
        strBldr.append("mul bx").append("\n");
        strBldr.append(generarComentario("AX = AX + CX (digito)")).append("\n");
        strBldr.append("add ax,cx").append("\n");
        strBldr.append(generarMov("si","ax")).append("\n");
        strBldr.append("endm").append("\n");
        strBldr.append("writeBS macro").append("\n");
        strBldr.append(generarMov("ah","0Eh")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append(generarMov("al","' '")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append(generarMov("ah","08h")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append("endm").append("\n");
        strBldr.append(generarInicioProcedimiento("readln")).append("\n");
        strBldr.append(generarPush("bp")).append("\n");
        strBldr.append(generarMov("bp","sp")).append("\n");
        strBldr.append(generarComentario("[bp-1] = estado (00h=inicio, 01h=solo 0, 02h=-, 03h=digitos)")).append("\n");
        strBldr.append("sub sp,1").append("\n");
        strBldr.append(generarComentario("[bp-2] = signo (00h=positivo, 01h=negativo)")).append("\n");
        strBldr.append("sub sp,1").append("\n");
        strBldr.append(generarPush("ax")).append("\n");
        strBldr.append(generarPush("bx")).append("\n");
        strBldr.append(generarPush("cx")).append("\n");
        strBldr.append(generarPush("dx")).append("\n");
        strBldr.append(generarPush("si")).append("\n");
        strBldr.append(generarMov("[bp-1]","00h")).append("\n");
        strBldr.append(generarMov("[bp-2]","00h")).append("\n");
        strBldr.append(generarComentario("valor")).append("\n");
        strBldr.append(generarMov("si","0000h")).append("\n");
        strBldr.append(generarMov("bx","0")).append("\n");
        strBldr.append(generarMov("cx","0")).append("\n");
        strBldr.append(generarLabel("inicioread")).append("\n");
        strBldr.append(generarMov("ah","0")).append("\n");
        strBldr.append("int 16h").append("\n");
        strBldr.append(generarCmp("[bp-1]","00h")).append("\n");
        strBldr.append("je estado0").append("\n");
        strBldr.append(generarCmp("[bp-1]","01h")).append("\n");
        strBldr.append("je estado1").append("\n");
        strBldr.append(generarCmp("[bp-1]","02h")).append("\n");
        strBldr.append("je estado2").append("\n");
        strBldr.append(generarCmp("[bp-1]","03h")).append("\n");
        strBldr.append("je estado3").append("\n");
        strBldr.append(generarLabel("estado0")).append("\n");
        strBldr.append(generarCmp("al","0Dh")).append("\n");
        strBldr.append("je inicioread").append("\n");
        strBldr.append(generarCmp("al","'0'")).append("\n");
        strBldr.append("jne estado0a").append("\n");
        strBldr.append(generarMov("[bp-1]","01h")).append("\n");
        strBldr.append(generarMov("ah","0Eh")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append(generarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(generarLabel("estado0a")).append("\n");
        strBldr.append(generarCmp("al","'-'")).append("\n");
        strBldr.append("jne estado0b").append("\n");
        strBldr.append(generarCmp("[bp+4]","0000h")).append("\n");
        strBldr.append("je inicioread").append("\n");
        strBldr.append(generarMov("[bp-1]","02h")).append("\n");
        strBldr.append(generarMov("[bp-2]","01h")).append("\n");
        strBldr.append(generarMov("ah","0Eh")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append(generarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(generarLabel("estado0b")).append("\n");
        strBldr.append("dig '1'").append("\n");
        strBldr.append(generarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(generarLabel("estado1")).append("\n");
        strBldr.append(generarCmp("al","0Dh")).append("\n");
        strBldr.append("je finread").append("\n");
        strBldr.append(generarCmp("al","08h")).append("\n");
        strBldr.append("jne inicioread").append("\n");
        strBldr.append("writeBS").append("\n");
        strBldr.append(generarMov("[bp-1]","00h")).append("\n");
        strBldr.append(generarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(generarLabel("estado2")).append("\n");
        strBldr.append(generarCmp("al","0Dh")).append("\n");
        strBldr.append("je inicioread").append("\n");
        strBldr.append(generarCmp("al","08h")).append("\n");
        strBldr.append("jne estado2a").append("\n");
        strBldr.append("writeBS").append("\n");
        strBldr.append(generarMov("[bp-1]","00h")).append("\n");
        strBldr.append(generarMov("[bp-2]","00h")).append("\n");
        strBldr.append(generarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(generarLabel("estado2a")).append("\n");
        strBldr.append("dig '1'").append("\n");
        strBldr.append(generarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(generarLabel("estado3")).append("\n");
        strBldr.append(generarCmp("al","0Dh")).append("\n");
        strBldr.append("je finread").append("\n");
        strBldr.append(generarCmp("al","08h")).append("\n");
        strBldr.append("jne estado3a").append("\n");
        strBldr.append("writeBS").append("\n");
        strBldr.append(generarMov("ax","si")).append("\n");
        strBldr.append(generarMov("dx","0")).append("\n");
        strBldr.append(generarMov("bx","000Ah")).append("\n");
        strBldr.append(generarComentario("AX = AX / 10")).append("\n");
        strBldr.append("div bx").append("\n");
        strBldr.append(generarMov("si","ax")).append("\n");
        strBldr.append(generarCmp("si","0")).append("\n");
        strBldr.append("jne inicioread").append("\n");
        strBldr.append(generarCmp("[bp-2]","00h")).append("\n");
        strBldr.append("jne estado3bs1").append("\n");
        strBldr.append(generarMov("[bp-1]","00h")).append("\n");
        strBldr.append(generarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(generarLabel("estado3bs1")).append("\n");
        strBldr.append(generarMov("[bp-1]","02h")).append("\n");
        strBldr.append(generarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(generarLabel("estado3a")).append("\n");
        strBldr.append("dig '0'").append("\n");
        strBldr.append(generarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(generarLabel("finread")).append("\n");
        strBldr.append(generarCmp("[bp-2]","00h")).append("\n");
        strBldr.append("je finread2").append("\n");
        strBldr.append("neg si").append("\n");
        strBldr.append(generarLabel("finread2")).append("\n");
        strBldr.append(generarMov("[bp+6]","si")).append("\n");
        strBldr.append(generarPop("si")).append("\n");
        strBldr.append(generarPop("dx")).append("\n");
        strBldr.append(generarPop("cx")).append("\n");
        strBldr.append(generarPop("bx")).append("\n");
        strBldr.append(generarPop("ax")).append("\n");
        strBldr.append(generarMov("sp","bp")).append("\n");
        strBldr.append(generarPop("bp")).append("\n");
        strBldr.append(generarFinProcedimiento("readln","2")).append("\n");
        strBldr.append(generarComentario("***Fin rutina readln***")).append("\n");
	        return strBldr.toString();
	    }
    
    private static String generarComentario(String comentario)
    {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append("; "+comentario);
        return strbldr.toString();
    }

    public static String generarPush(String registro)
    {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append(String.format("PUSH\t%s", registro));
        return strbldr.toString();
    }

    public static String generarPop(String registro)
    {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append(String.format("POP\t%s", registro));
        return strbldr.toString();
    }
    
    public static String generarMov(String destino, String desde)
    {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append("MOV\t").append(destino).append(", ").append(desde);
        return strbldr.toString();
    }


    public static String generarInicioProcedimiento(String label)
    {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append(label).append(" PROC NEAR\n");
        return strbldr.toString();
    }
    
    public static String generarLabel(String label)
    {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append(label).append(":").append("\n");
        return strbldr.toString();
    }
    
    
    public static String generarFinProcedimiento(String label,String valor)
    {
        StringBuilder strbldr = new StringBuilder();
        if (valor != "")
            strbldr.append(" RET ").append(valor).append("\n");
        else
            strbldr.append(" RET\n");
        strbldr.append(label).append(" ENDP\n");
        return strbldr.toString();
    }
    
    public static String generarCmp(String destino, String desde)
    {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append("CMP\t").append(destino).append(", ").append(desde);
        return strbldr.toString();
    }

    public static String generarCall(String label)
    {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append("CALL\t").append(label);
        return strbldr.toString();
    } 
    
    public static String generarJumpIncondicional(String codigoDestino)
    {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append("JMP\t").append(codigoDestino);
        return strbldr.toString();
    }

}
