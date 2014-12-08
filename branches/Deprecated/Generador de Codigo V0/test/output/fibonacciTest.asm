include emu8086.inc

programa SEGMENT
ASSUME CS:programa, DS:programa, ES:programa, SS:programa
ORG 0100h

DEFINE_PRINT_NUM
DEFINE_PRINT_NUM_UNS
DEFINE_SCAN_NUM

JMP inicio

inicio:
call main

MOV AH, 4Ch
INT 21h


; Comienza la delcaracion de Var globales
; Finaliza la delcaracion de Globales

;Inicio Funcion fibonacci
PROC fibonacci
	push bp
	mov bp, sp
	
	mov DX, sp; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
	fibfibonacci DW 0
	naux1fibonacci DW 0
	naux2fibonacci DW 0
	afibonacci DW 0
	bfibonacci DW 0
	cfibonacci DW 0
	mov sp, DX; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
	
	; Comienzo de las Sentencias
	MOV AX, 0; Para evitar el arrastre de Basura
	MOV AX,[bp+4]
	CMP AX,3
	JL Less1215338227;
	MOV AX,0
	JMP Fin1215338227;
	Less1215338227:
	MOV AX,1
	Fin1215338227:
	cmp ax,1
	jne ElseBlk1472614572;
	MOV AX,1
	MOV fibfibonacci, AX
	jmp EndOfIf484079929;
	ElseBlk1472614572:
	MOV AX,[bp+4]
	SUB AX,2
	MOV naux1fibonacci, AX
	MOV AX,[bp+4]
	SUB AX,1
	MOV naux2fibonacci, AX
	
	; Muevo las Variables al Stack
	MOV BX, 0
	MOV BX, fibfibonacci
	push BX
	MOV BX, 0
	MOV BX, naux1fibonacci
	push BX
	MOV BX, 0
	MOV BX, naux2fibonacci
	push BX
	MOV BX, 0
	MOV BX, afibonacci
	push BX
	MOV BX, 0
	MOV BX, bfibonacci
	push BX
	MOV BX, 0
	MOV BX, cfibonacci
	push BX
	; Muevo los Temporales al Stack
	; Llamo a la funcion
	MOV AX,naux1fibonacci
	push AX
	call fibonacci
	MOV DX, AX ;Guardo el retorno en un Registro Seguro
	MOV CX, bp
	; Recupero los temporales del Stack
	; Recupero las Variables del Stack
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV cfibonacci, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV bfibonacci, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV afibonacci, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV naux2fibonacci, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV naux1fibonacci, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV fibfibonacci, BX
	; Fin del Recupero
	MOV AX, 0 ;
	MOV AX, DX ;Recupero el retorno
	
	MOV afibonacci, AX
	
	; Muevo las Variables al Stack
	MOV BX, 0
	MOV BX, fibfibonacci
	push BX
	MOV BX, 0
	MOV BX, naux1fibonacci
	push BX
	MOV BX, 0
	MOV BX, naux2fibonacci
	push BX
	MOV BX, 0
	MOV BX, afibonacci
	push BX
	MOV BX, 0
	MOV BX, bfibonacci
	push BX
	MOV BX, 0
	MOV BX, cfibonacci
	push BX
	; Muevo los Temporales al Stack
	; Llamo a la funcion
	MOV AX,naux2fibonacci
	push AX
	call fibonacci
	MOV DX, AX ;Guardo el retorno en un Registro Seguro
	MOV CX, bp
	; Recupero los temporales del Stack
	; Recupero las Variables del Stack
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV cfibonacci, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV bfibonacci, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV afibonacci, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV naux2fibonacci, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV naux1fibonacci, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV fibfibonacci, BX
	; Fin del Recupero
	MOV AX, 0 ;
	MOV AX, DX ;Recupero el retorno
	
	MOV bfibonacci, AX
	MOV AX,afibonacci
	ADD AX,bfibonacci
	MOV fibfibonacci, AX
	EndOfIf484079929:
	; Fin de las Sentencias
	MOV AX,fibfibonacci
	pop bp
RET 2
ENDP
;Fin Funcion fibonacci

;Inicio Procedimiento main
PROC main
	mov DX, sp; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
	nromain DW 0
	imain DW 0
	rtamain DW 0
	mov sp, DX; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
	
	; Comienzo de las Sentencias
	MOV AX, 0; Para evitar el arrastre de Basura
	MOV AX,0
	MOV nromain, AX
	MOV AX,1
	MOV imain, AX
	While1263311867:
	MOV AX,nromain
	CMP AX,1
	JL Less311730780;
	MOV AX,0
	JMP Fin311730780;
	Less311730780:
	MOV AX,1
	Fin311730780:
	cmp ax,1
	jne EndWhile1263311867;
	MOV AX,0
	MOV BX,0
	MOV DX,0
	PRINT "'Ingrese un numero Entero: '"
	MOV AX,0
	MOV BX,0
	MOV DX,0
	call SCAN_NUM
	mov nromain,cx
	PRINTN ""
	MOV AX,0
	MOV BX,0
	MOV DX,0
	PRINT "' '"
	PRINTN ""
	MOV AX,0
	MOV BX,0
	MOV DX,0
	jmp While1263311867;
	EndWhile1263311867:
	While1546604427:
	MOV AX,imain
	MOV CX,sp ;Para evitar el corrimiento
	MOV BX, 0 ;Sino se comporta muy extrano
	MOV DX, AX ;Sino se comporta muy extrano
	temp1main DW 0
	MOV AX, 0 ;Arrastra basura la declaracion!!
	MOV AL, 0 ;Arrastra basura la declaracion!!
	MOV AH, 0 ;Arrastra basura la declaracion!!
	MOV AX, DX ;Sino se comporta muy extrano
	MOV sp,CX ;Recupero el corrimiento
	MOV temp1main,AX
	MOV AX,nromain
	ADD AX,1
	MOV CX,sp ;Para evitar el corrimiento
	MOV BX, 0 ;Sino se comporta muy extrano
	MOV DX, AX ;Sino se comporta muy extrano
	temp2main DW 0
	MOV AX, 0 ;Arrastra basura la declaracion!!
	MOV AL, 0 ;Arrastra basura la declaracion!!
	MOV AH, 0 ;Arrastra basura la declaracion!!
	MOV AX, DX ;Sino se comporta muy extrano
	MOV sp,CX ;Recupero el corrimiento
	MOV temp2main,AX
	MOV AX,temp1main
	CMP AX,temp2main
	JL Less638541301;
	MOV AX,0
	JMP Fin638541301;
	Less638541301:
	MOV AX,1
	Fin638541301:
	cmp ax,1
	jne EndWhile1546604427;
	
	; Muevo las Variables al Stack
	MOV BX, 0
	MOV BX, nromain
	push BX
	MOV BX, 0
	MOV BX, imain
	push BX
	MOV BX, 0
	MOV BX, rtamain
	push BX
	; Muevo los Temporales al Stack
	; Llamo a la funcion
	MOV AX,imain
	push AX
	call fibonacci
	MOV DX, AX ;Guardo el retorno en un Registro Seguro
	MOV CX, bp
	; Recupero los temporales del Stack
	; Recupero las Variables del Stack
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV rtamain, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV imain, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV nromain, BX
	; Fin del Recupero
	MOV AX, 0 ;
	MOV AX, DX ;Recupero el retorno
	
	MOV rtamain, AX
	MOV AX,0
	MOV BX,0
	MOV DX,0
	MOV AX,imain
	call PRINT_NUM
	PRINT "':'"
	MOV AX,rtamain
	call PRINT_NUM
	PRINTN ""
	MOV AX,0
	MOV BX,0
	MOV DX,0
	MOV AX,imain
	ADD AX,1
	MOV imain, AX
	jmp While1546604427;
	EndWhile1546604427:
	; Fin de las Sentencias
RET
ENDP
;Fin Procedimiento main

programa ENDS
