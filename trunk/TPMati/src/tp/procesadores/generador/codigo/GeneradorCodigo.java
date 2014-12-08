package tp.procesadores.generador.codigo;

public class GeneradorCodigo {
	
	 public int INDICE_FUERA_DE_RANGO = 1;

     public static String GenerarCodigoInicial(String labelMain)
     {
         StringBuilder strbldr = new StringBuilder();
         //codigo obligatorio de ejecucion
         strbldr.append("SEG Segment\n");
         strbldr.append("ASSUME CS:SEG,SS:SEG,DS:SEG,ES:SEG\n");
         strbldr.append("ORG 0100h\n");
         
         return strbldr.toString();
     }
	
     public static String GenerarInicioProcedimiento(String label)
     {
         StringBuilder strbldr = new StringBuilder();

         strbldr.append(label).append(" PROC NEAR\n");

         return strbldr.toString();

     }
     public static String GenerarFinProcedimiento(String label,String valor)
     {
         StringBuilder strbldr = new StringBuilder();

         if (valor != "")
         {
             strbldr.append(" RET ").append(valor).append("\n");
         }
         else
         {
             strbldr.append(" RET\n");
         }
         strbldr.append(label).append(" ENDP\n");

         return strbldr.toString();

     }

    public static String GenerarComentario(String comentario)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append("; "+comentario);

        return strbldr.toString();
    }

    public static String GenerarDefinicion(String label)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append(label).append(" dw ?");

        return strbldr.toString();
    }
    
    public static String GenerarDefinicion(String label,String value)
    {
        StringBuilder strbldr = new StringBuilder();
        if (value!="")
            strbldr.append(label + " dw '"+value+"'");
        else
            strbldr.append(label + " dw ?");

        return strbldr.toString();
    }
    
    public static String GenerarLabel(String label)
    {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append(label).append(":").append("\n");
        return strbldr.toString();
    }

    public static String GenerarParam(String label)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append("PARAM\t").append(label);

        return strbldr.toString();
    }

    public static String GenerarCall(String label)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append("CALL\t").append(label);

        return strbldr.toString();
    }

    public static String GenerarMov(String destino, String desde)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append("MOV\t").append(destino).append(", ").append(desde);

        return strbldr.toString();
    }

    public static String GenerarMovHaciaAx(String desde)
    {
        return GeneradorCodigo.GenerarMov("AX", desde);
    }

    public static String GenerarMovDesdeAx(String hacia)
    {
        return GeneradorCodigo.GenerarMov(hacia, "AX");
    }

    public static String GenerarCmp(String destino, String desde)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append("CMP\t").append(destino).append(", ").append(desde);

        return strbldr.toString();
    }

// Ver que onda con el tema de generar JUMPs
/*    
    public static string GenerarJump(string label, NodoArbolSemantico.TipoComparacion comp)
    {
        StringBuilder strbldr = new StringBuilder();

        switch (comp)
        {
            case NodoArbolSemantico.TipoComparacion.Greater:
                strbldr.Append("JG\t").append(label);
                break;
            case NodoArbolSemantico.TipoComparacion.GreaterOrEquals:
                strbldr.Append("JGE\t").append(label);
                break;                
            case NodoArbolSemantico.TipoComparacion.Less:
                strbldr.Append("JL\t").append(label);
                break;
            case NodoArbolSemantico.TipoComparacion.LessOrEquals:
                strbldr.Append("JLE\t").append(label);
                break;
            
            case NodoArbolSemantico.TipoComparacion.Above:
                strbldr.Append("JA\t").append(label);
                break;
            case NodoArbolSemantico.TipoComparacion.AboveOrEquals:
                strbldr.Append("JAE\t").append(label);
                break;
            case NodoArbolSemantico.TipoComparacion.Below:
                strbldr.Append("JB\t").append(label);
                break;
            case NodoArbolSemantico.TipoComparacion.BelowOrEquals:
                strbldr.Append("JBE\t").append(label);
                break;

            case NodoArbolSemantico.TipoComparacion.Equals:
                strbldr.Append("JE\t").append(label);
                break;
            case NodoArbolSemantico.TipoComparacion.NotEquals:
                strbldr.Append("JNE\t").append(label);
                break;

            case NodoArbolSemantico.TipoComparacion.EqualZero:
                strbldr.Append("JZ\t").append(label);
                break;
            case NodoArbolSemantico.TipoComparacion.NotEqualZero:
                strbldr.Append("JNZ\t").append(label);
                break;

            case NodoArbolSemantico.TipoComparacion.LessThanZero:
                strbldr.Append("JS\t").append(label);
                break;
            case NodoArbolSemantico.TipoComparacion.GreaterThanZero:
                strbldr.Append("JNS\t").append(label);
                break;

        }

        return strbldr.ToString();
    }    
    */

    public static String GenerarJumpIncondicional(String codigoDestino)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append("JMP\t").append(codigoDestino);

        return strbldr.toString();
    }

    public static String GenerarLea(String destino, String origen)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append(String.format("LEA\t%s, %s", destino, origen));

        return strbldr.toString();
    }

    public static String GenerarInc(String registro)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append(String.format("INC\t%s", registro));

        return strbldr.toString();
    }

    public static String GenerarLoop(String label)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append(String.format("LOOP\t%s", label));

        return strbldr.toString();
    }

    public static String ExprBool(String op1, String op2)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(GeneradorCodigo.GenerarMov("AX", op1)).append("\n");
        sb.append(GeneradorCodigo.GenerarCmp("AX", op2)).append("\n");

        return sb.toString();
    }

    public static String AssignArray(String labelVar, String valor)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(GeneradorCodigo.GenerarMov("CX", valor)).append("\n");
        sb.append(GeneradorCodigo.GenerarMov("BX", "2")).append("\n");
        sb.append(String.format("MUL\t%s", "BX")).append("\n");
        sb.append(GeneradorCodigo.GenerarMov("DI", "AX")).append("\n");
        sb.append(GeneradorCodigo.GenerarMov(String.format("%s[%s]", labelVar, "DI"), "CX")).append("\n");

        return sb.toString();
    }

    public static String MoveArray(String labelVar)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(GeneradorCodigo.GenerarLabel("L1"));
        sb.append(String.format("ADD\t%s, %s", "BX", "2")).append("\n");
        sb.append(GeneradorCodigo.GenerarLoop("L1"));
        sb.append(String.format("SUB\t%s, %s", "BX", "2")).append("\n");

        return sb.toString();
    }

    public static String OperarAritmeticamente(String operador, String labelRetorno, String op1, String op2)
    {
        StringBuilder sb = new StringBuilder();

        
        if (operador.equals("+") || operador.equals("++"))
        {
        	sb.append(GeneradorCodigo.GenerarSuma(labelRetorno, op1, op2));
        }
        else
        {
        	 if (operador.equals("-") || operador.equals("--"))
        	 {
        	     sb.append(GeneradorCodigo.GenerarResta(labelRetorno, op1, op2));
        	 }
        	 else
        	 {
            	 if (operador.equals("*") || operador.equals("**"))
            	 { 
            		 sb.append(GeneradorCodigo.GenerarMultiplicacion(labelRetorno, op1, op2));
            	 }
            	 else
            	 {     
                	 if (operador.equals("/") || operador.equals("//"))
                	 {
                		 sb.append(GeneradorCodigo.GenerarDivision(labelRetorno, op1, op2));
                	 }
            	 }
        	 }
        }
        
        return sb.toString();
    }
	

    public static String GenerarSuma(String label, String op1, String op2)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append(GenerarMov("AX", op1)).append("\n");
        strbldr.append(String.format("ADD\t%s, %s", "AX", op2)).append("\n");
        strbldr.append(GenerarMov(label, "AX")).append("\n");

        return strbldr.toString();
    }

    public static String GenerarResta(String label, String op1, String op2)
    {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append(GenerarMov("AX", op1)).append("\n");
        strbldr.append(String.format("SUB\t%s, %s", "AX", op2)).append("\n");
        strbldr.append(GenerarMov(label, "AX")).append("\n");
        return strbldr.toString();
    }

    public static String GenerarMultiplicacion(String label, String op1, String op2)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append(GenerarMovHaciaAx(op2)).append("\n");
        strbldr.append(GenerarMovDesdeAx("divisorAuxiliar")).append("\n");
        strbldr.append(GenerarMov("AX", op1)).append("\n");
        strbldr.append(String.format("IMUL\t%s","divisorAuxiliar")).append("\n");
        strbldr.append(GenerarMov(label, "AX")).append("\n");

        return strbldr.toString();
    }

    public static String GenerarDivision(String label, String op1, String op2)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append(GenerarMovHaciaAx(op2)).append("\n");
        strbldr.append(String.format("ADD\t%s, %s", "AX", "0")).append("\n");
        //Para no dividr por cero
        
//      Ver como manejar el tema de divsion por cero
//      strbldr.append(GenerarJump("labelDivisionPorCero", NodoArbolSemantico.TipoComparacion.EqualZero)).append("\n");

        strbldr.append(GenerarMovDesdeAx("divisorAuxiliar")).append("\n");
        strbldr.append(GenerarMov("DX", "0")).append("\n");
        strbldr.append(GenerarMov("AX", op1)).append("\n");
        strbldr.append(String.format("IDIV\t%s", "divisorAuxiliar")).append("\n");
        strbldr.append(GenerarMov(label, "AX")).append("\n");

        return strbldr.toString();
    }

    public static String GenerarMultiplicacionNatural(String label, String op1, String op2)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append(GenerarMovHaciaAx(op2)).append("\n");
        strbldr.append(GenerarMovDesdeAx("divisorAuxiliar")).append("\n");
        strbldr.append(GenerarMov("AX", op1)).append("\n");
        strbldr.append(String.format("MUL\t%s", "divisorAuxiliar")).append("\n");
        strbldr.append(GenerarMov(label, "AX")).append("\n");

        return strbldr.toString();
    }

    public static String GenerarDivisionNatural(String label, String op1, String op2)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append(GenerarMovHaciaAx(op2)).append("\n");
        strbldr.append(String.format("ADD\t%s, %s", "AX", "0")).append("\n");
        //Para no dividr por cero
        
//        Ver como manejar el tema de divsion por cero
//        strbldr.append(GenerarJump("labelDivisionPorCero", NodoArbolSemantico.TipoComparacion.EqualZero)).append("\n");        
        
        strbldr.append(GenerarMovDesdeAx("divisorAuxiliar")).append("\n");
        strbldr.append(GenerarMov("DX", "0")).append("\n");
        strbldr.append(GenerarMov("AX", op1)).append("\n");           
        strbldr.append(String.format("DIV\t%s", "divisorAuxiliar")).append("\n");
        strbldr.append(GenerarMov(label, "AX")).append("\n");

        return strbldr.toString();
    }

    public static String GenerarPush(String registro)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append(String.format("PUSH\t%s", registro));

        return strbldr.toString();
    }

    public static String GenerarPop(String registro)
    {
        StringBuilder strbldr = new StringBuilder();

        strbldr.append(String.format("POP\t%s", registro));

        return strbldr.toString();
    }


    public static String GenerarEsPar(String labelVariable)
    {
        StringBuilder strbldr = new StringBuilder();

        //strbldr.append(GenerarMov("divisorAuxiliar", "2"));
        //strbldr.append(GenerarMov("DX", "0"));
        //strbldr.append(GenerarMov("AX", labelVariable));
        //strbldr.append(String.format("IDIV\t%s", "divisorAuxiliar"));
        //strbldr.append(GenerarCmp("DX", "0"));

        strbldr.append(GenerarMov("Ax", labelVariable));
        strbldr.append(String.format("TEST\t Ax, 0001h"));

        return strbldr.toString();
    }

    
    
    // Ver que onda
    /*
    public static String GenerarError(String labelError, String nombreVarError, int cantChars )
    {
        StringBuilder strBldr = new StringBuilder();
        strBldr.append(GeneradorCodigo.GenerarLabel(labelError));
        strBldr.append("PUSH OFFSET\t%s", nombreVarError).append();
        strBldr.appendformat("PUSH\t%s", cantChars.toString()).append();
        strBldr.append(GeneradorCodigo.GenerarCall("writeSTR"));
        strBldr.append("INT\t21h");

        return strBldr.toString();
    }
    

    public static String GenerarJumpError(int codigoError)
    {
        var valorHexa = codigoError.ToString("X");

        valorHexa = new string('0', 4 - valorHexa.Length) + valorHexa + "h";
        StringBuilder strBldr = new StringBuilder();


        strBldr.append("MOV\tAH," + valorHexa);
        strBldr.append("JMP\tFIN");

        return strBldr.ToString();
    }
     */

    public static String GenerarFinalizacion()
    {
        StringBuilder strBldr = new StringBuilder();


        strBldr.append("MOV\tAH,4Ch").append("\n");
        strBldr.append("FIN:INT\t21h").append("\n");

        return strBldr.toString();
    }

    public static String GenerarProcedimientosWrite()
    {
        StringBuilder strBldr = new StringBuilder();
        strBldr.append(GenerarProcedimientoWriteStr()).append("\n");
        strBldr.append(GenerarProcedimientoWriteCtr()).append("\n");
        strBldr.append(GenerarProcedimientoWriteNum()).append("\n");
        return strBldr.toString();
    }
    private static String GenerarProcedimientoWriteStr()
    {
        StringBuilder strBldr = new StringBuilder();
        strBldr.append(GenerarComentario("***Comienzo rutina writeSTR***")).append("\n");
        strBldr.append(GenerarComentario("Imprime por pantalla un string sin <Enter> al final")).append("\n");
        strBldr.append(GenerarComentario("Parametros:")).append("\n");
        strBldr.append(GenerarComentario("Parametro entrada 1: direccion de memoria donde comienza el string a imprimir (word/via push del llamador)")).append("\n");
        strBldr.append(GenerarComentario("Parametro entrada 2: cantidad de caracteres del string (word/via push del llamador)")).append("\n");
        strBldr.append(GenerarInicioProcedimiento("writeSTR")).append("\n");
        strBldr.append(GenerarPush("bp")).append("\n");
        strBldr.append(GenerarMov("bp","sp")).append("\n");
        strBldr.append(GenerarPush("ax")).append("\n");
        strBldr.append(GenerarPush("bx")).append("\n");
        strBldr.append(GenerarPush("cx")).append("\n");
        strBldr.append(GenerarPush("si")).append("\n");
        strBldr.append(GenerarComentario("a SI se le asigna el primer parametro (direccion)")).append("\n");
        strBldr.append(GenerarMov("si","[bp+6]")).append("\n");
        strBldr.append(GenerarComentario("a CX se le asigna el segundo parametro (cantidad de caracteres)")).append("\n");
        strBldr.append(GenerarMov("si","[bp+4]")).append("\n");
        strBldr.append("xor bx,bx").append("\n");
        strBldr.append(GenerarLabel("loop")).append("\n");
        strBldr.append(GenerarMov("al","[si]")).append("\n");
        strBldr.append(GenerarMov("ah","0Eh")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append("inc bx").append("\n");
        strBldr.append("inc si").append("\n");
        strBldr.append(GenerarCmp("bx","cx")).append("\n");
        strBldr.append(GenerarPop("si")).append("\n");
        strBldr.append(GenerarPop("cx")).append("\n");
        strBldr.append(GenerarPop("bx")).append("\n");
        strBldr.append(GenerarPop("ax")).append("\n");
        strBldr.append(GenerarPop("bp")).append("\n");
        strBldr.append(GenerarFinProcedimiento("writeSTR","4")).append("\n");
        strBldr.append(GenerarComentario("***Fin rutina writeSTR***")).append("\n");
        return strBldr.toString();
    }
    private static String GenerarProcedimientoWriteCtr()
    {
        StringBuilder strBldr = new StringBuilder();
        strBldr.append(GenerarComentario("***Comienzo rutina writeCRLF***")).append("\n");
        strBldr.append(GenerarComentario("Imprime por pantalla un caracter <Enter> (<CR><LF>)")).append("\n");
        strBldr.append(GenerarComentario("Parametros:")).append("\n");
        strBldr.append(GenerarInicioProcedimiento("writeCRLF")).append("\n");
        strBldr.append(GenerarPush("AX")).append("\n");
        strBldr.append(GenerarMov("al","0Dh")).append("\n");
        strBldr.append(GenerarMov("al","0Eh")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append(GenerarMov("al","0Ah")).append("\n");
        strBldr.append(GenerarMov("al","0Eh")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append(GenerarPop("AX")).append("\n");
        strBldr.append(GenerarFinProcedimiento("writeCRLF","")).append("\n");
        strBldr.append(GenerarComentario("***Fin rutina writeCRLF***")).append("\n");
        return strBldr.toString();
    }
    private static String GenerarProcedimientoWriteNum()
    {
        StringBuilder strBldr = new StringBuilder();
        strBldr.append(GenerarComentario("***Comienzo rutina writeNUM***")).append("\n");
        strBldr.append(GenerarComentario("Imprime por pantalla un numero (word con signo) sin <Enter> al final")).append("\n");
        strBldr.append(GenerarComentario("Parametros:")).append("\n");
        strBldr.append(GenerarComentario("Parametro entrada 1: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador)")).append("\n");
        strBldr.append(GenerarComentario("Parametro entrada 2: numero a imprimir (word/via push del llamador)")).append("\n");
        strBldr.append(GenerarInicioProcedimiento("writeNUM")).append("\n");
        strBldr.append(GenerarPush("bp")).append("\n");
        strBldr.append(GenerarMov("bp", "sp")).append("\n");
        strBldr.append(GenerarComentario("variable local/ [bp-1] = signo (00h=positivo, 01h=negativo)")).append("\n");
        strBldr.append("sub sp,1").append("\n");
        strBldr.append(GenerarComentario("variable local/ [bp-7] = espacio para imprimir (db 6 dup(?))")).append("\n");
        strBldr.append("sub sp,6").append("\n");
        strBldr.append(GenerarPush("ax")).append("\n");
        strBldr.append(GenerarPush("bx")).append("\n");
        strBldr.append(GenerarPush("cx")).append("\n");
        strBldr.append(GenerarPush("dx")).append("\n");
        strBldr.append(GenerarPush("si")).append("\n");
        strBldr.append(GenerarMov("[bp-1]", "00h")).append("\n");
        strBldr.append(GenerarMov("ax", "[bp+4]")).append("\n");
        strBldr.append(GenerarCmp("[bp+6]", "0")).append("\n");
        strBldr.append(GenerarComentario("Si no es aritmetica con signo, comienza")).append("\n");
        strBldr.append(GenerarCmp("ax", "0")).append("\n");
        strBldr.append(GenerarComentario("Si no es negativo, comienza")).append("\n");
        strBldr.append(GenerarComentario("Realiza ax = -ax")).append("\n");
        strBldr.append("neg ax").append("\n");
        strBldr.append(GenerarMov("[bp-1]", "01h")).append("\n");
        strBldr.append(GenerarLabel("comenzar")).append("\n");
        strBldr.append(GenerarMov("bx", "10")).append("\n");
        strBldr.append(GenerarMov("cx", "0")).append("\n");
        strBldr.append(GenerarMov("si", "bp")).append("\n");
        strBldr.append(" sub si,8").append("\n");
        strBldr.append(GenerarLabel("proxdiv")).append("\n");
        strBldr.append(" dec si").append("\n");
        strBldr.append(" xor dx,dx").append("\n");
        strBldr.append(" div bx").append("\n");
        strBldr.append(" add dl,48").append("\n");
        strBldr.append(GenerarMov("[si]", "dl")).append("\n");
        strBldr.append(" inc cx").append("\n");
        strBldr.append(GenerarCmp("ax", "0")).append("\n");
        strBldr.append(GenerarCmp("[bp-1]", "00h")).append("\n");
        strBldr.append(" dec si").append("\n");
        strBldr.append(GenerarMov("[si]", "-")).append("\n");
        strBldr.append(" inc cx").append("\n");
        strBldr.append(GenerarLabel("mostrar")).append("\n");
        strBldr.append(GenerarPush("si")).append("\n");
        strBldr.append(GenerarPush("cx")).append("\n");
        strBldr.append(GenerarCall("writeSTR")).append("\n");
        strBldr.append(GenerarPop("si")).append("\n");
        strBldr.append(GenerarPop("dx")).append("\n");
        strBldr.append(GenerarPop("cx")).append("\n");
        strBldr.append(GenerarPop("bx")).append("\n");
        strBldr.append(GenerarPop("ax")).append("\n");
        strBldr.append(GenerarMov("sp", "bp")).append("\n");
        strBldr.append(GenerarPop("bp")).append("\n");
        strBldr.append(GenerarFinProcedimiento("writeNUM","4")).append("\n");
        strBldr.append(GenerarComentario("***Fin rutina writeNUM***")).append("\n");
        return strBldr.toString();
    }
    
    public static String GenerarProcedimientoRead()
    {
        StringBuilder strBldr = new StringBuilder();
        strBldr.append(GenerarComentario("***Comienzo rutina readln***")).append("\n");
        strBldr.append(GenerarComentario("Obtiene por teclado un numero (word con o sin signo)")).append("\n");
        strBldr.append(GenerarComentario("(usa rutina writeSTR, notar que posee dos macros)")).append("\n");
        strBldr.append(GenerarComentario("Solamente permite ingresar caracteres <0>..<9>, <->, <Backspace>, <Enter> (cuando corresponden)")).append("\n");
        strBldr.append(GenerarComentario("No realiza control de overflow, ni permite <Control><Break> para cortar la ejecucion del programa")).append("\n");
        strBldr.append(GenerarComentario("Parametros:"));
        strBldr.append(GenerarComentario("Parametro entrada: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador)")).append("\n");
        strBldr.append(GenerarComentario("Parametro salida: numero obtenido (word/via pop del llamador)"));
        strBldr.append(GenerarComentario("(el llamador antes debera efectuar un push de un word para que esta rutina deje el resultado ahi)")).append("\n");
        strBldr.append("dig macro digbase").append("\n");
        strBldr.append(GenerarCmp("al","digbase")).append("\n");
        strBldr.append(GenerarCmp("al","'9'")).append("\n");
        strBldr.append(GenerarMov("ah","0Eh")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append(GenerarMov("[bp-1]","03h")).append("\n");
        strBldr.append(GenerarMov("cl","al")).append("\n");
        strBldr.append("sub cl,48").append("\n");
        strBldr.append(GenerarMov("ax","si")).append("\n");
        strBldr.append(GenerarMov("bx","00Ah")).append("\n");
        strBldr.append("mul bx").append("\n");
        strBldr.append(GenerarComentario("AX = AX * 10")).append("\n");
        strBldr.append("add ax,cx").append("\n");
        strBldr.append(GenerarComentario("AX = AX + CX (digito)")).append("\n");
        strBldr.append(GenerarMov("si","ax")).append("\n");
        strBldr.append("endm").append("\n");
        strBldr.append("writeBS macro").append("\n");
        strBldr.append(GenerarMov("ah","0Eh")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append(GenerarMov("al","' '")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append(GenerarMov("ah","08h")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append("endm").append("\n");
        strBldr.append(GenerarInicioProcedimiento("readln")).append("\n");
        strBldr.append(GenerarPush("bp")).append("\n");
        strBldr.append(GenerarMov("bp","sp")).append("\n");
        strBldr.append(GenerarComentario("[bp-1] = estado (00h=inicio, 01h=solo 0, 02h=-, 03h=digitos)")).append("\n");
        strBldr.append("sub sp,1").append("\n");
        strBldr.append(GenerarComentario("[bp-2] = signo (00h=positivo, 01h=negativo)")).append("\n");
        strBldr.append("sub sp,1").append("\n");
        strBldr.append(GenerarPush("ax")).append("\n");
        strBldr.append(GenerarPush("bx")).append("\n");
        strBldr.append(GenerarPush("cx")).append("\n");
        strBldr.append(GenerarPush("dx")).append("\n");
        strBldr.append(GenerarPush("si")).append("\n");
        strBldr.append(GenerarMov("[bp-1]","00h")).append("\n");
        strBldr.append(GenerarMov("[bp-2]","00h")).append("\n");
        strBldr.append(GenerarMov("si","0000h")).append("\n");
        strBldr.append(GenerarMov("bx","0")).append("\n");
        strBldr.append(GenerarMov("cx","0")).append("\n");
        strBldr.append(GenerarLabel("inicioread")).append("\n");
        strBldr.append(GenerarMov("ah","0")).append("\n");
        strBldr.append("int 16h").append("\n");
        strBldr.append(GenerarCmp("[bp-1]","00h")).append("\n");
        strBldr.append(GenerarCmp("[bp-1]","01h")).append("\n");
        strBldr.append(GenerarCmp("[bp-1]","02h")).append("\n");
        strBldr.append(GenerarCmp("[bp-1]","03h")).append("\n");
        strBldr.append(GenerarLabel("estado0")).append("\n");
        strBldr.append(GenerarCmp("al","0Dh")).append("\n");
        strBldr.append(GenerarCmp("al","'0'")).append("\n");
        strBldr.append(GenerarMov("[bp-1]","01h")).append("\n");
        strBldr.append(GenerarMov("ah","0Eh")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append(GenerarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(GenerarLabel("estado0a")).append("\n");
        strBldr.append(GenerarCmp("al","'-'")).append("\n");
        strBldr.append(GenerarCmp("[bp+4]","0000h")).append("\n");
        strBldr.append(GenerarMov("[bp-1]","02h")).append("\n");
        strBldr.append(GenerarMov("[bp-2]","01h")).append("\n");
        strBldr.append(GenerarMov("ah","0Eh")).append("\n");
        strBldr.append("int 10h").append("\n");
        strBldr.append(GenerarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(GenerarLabel("estado0b")).append("\n");
        strBldr.append("dig '1'").append("\n");
        strBldr.append(GenerarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(GenerarLabel("estado1")).append("\n");
        strBldr.append(GenerarCmp("al","0Dh")).append("\n");
        strBldr.append(GenerarCmp("al","08h")).append("\n");
        strBldr.append("writeBS").append("\n");
        strBldr.append(GenerarMov("[bp-1]","00h")).append("\n");
        strBldr.append(GenerarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(GenerarLabel("estado2")).append("\n");
        strBldr.append(GenerarCmp("al","0Dh")).append("\n");
        strBldr.append(GenerarCmp("al","08h")).append("\n");
        strBldr.append("writeBS").append("\n");
        strBldr.append(GenerarMov("[bp-1]","00h")).append("\n");
        strBldr.append(GenerarMov("[bp-2]","00h")).append("\n");
        strBldr.append(GenerarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(GenerarLabel("estado2a")).append("\n");
        strBldr.append("dig '1'").append("\n");
        strBldr.append(GenerarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(GenerarLabel("estado3")).append("\n");
        strBldr.append(GenerarCmp("al","0Dh")).append("\n");
        strBldr.append(GenerarCmp("al","08h")).append("\n");
        strBldr.append("writeBS").append("\n");
        strBldr.append(GenerarMov("ax","si")).append("\n");
        strBldr.append(GenerarMov("dx","0")).append("\n");
        strBldr.append(GenerarMov("bx","000Ah")).append("\n");
        strBldr.append("div bx").append("\n");
        strBldr.append(GenerarMov("si","ax")).append("\n");
        strBldr.append(GenerarCmp("si","0")).append("\n");
        strBldr.append(GenerarCmp("[bp-2]","00h")).append("\n");
        strBldr.append(GenerarMov("[bp-1]","00h")).append("\n");
        strBldr.append(GenerarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(GenerarLabel("estado3bs1")).append("\n");
        strBldr.append(GenerarMov("[bp-1]","02h")).append("\n");
        strBldr.append(GenerarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(GenerarLabel("estado3a")).append("\n");
        strBldr.append("dig '0'").append("\n");
        strBldr.append(GenerarJumpIncondicional("inicioread")).append("\n");
        strBldr.append(GenerarLabel("finread")).append("\n");
        strBldr.append(GenerarCmp("[bp-2]","00h")).append("\n");
        strBldr.append("neg si").append("\n");
        strBldr.append(GenerarLabel("finread2")).append("\n");
        strBldr.append(GenerarMov("[bp+6]","si")).append("\n");
        strBldr.append(GenerarPop("si")).append("\n");
        strBldr.append(GenerarPop("dx")).append("\n");
        strBldr.append(GenerarPop("cx")).append("\n");
        strBldr.append(GenerarPop("bx")).append("\n");
        strBldr.append(GenerarPop("ax")).append("\n");
        strBldr.append(GenerarMov("sp","bp")).append("\n");
        strBldr.append(GenerarPop("bp")).append("\n");
        strBldr.append(GenerarFinProcedimiento("readln","2")).append("\n");
        strBldr.append(GenerarComentario("***Fin rutina readln***")).append("\n");
        return strBldr.toString();
    }
}



