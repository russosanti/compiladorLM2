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

;Inicio Funcion javi
PROC javi
	push bp
	mov bp, sp
	
	mov DX, sp; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
	mov sp, DX; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
	
	; Comienzo de las Sentencias
	MOV AX, 0; Para evitar el arrastre de Basura
	MOV AX,[bp+4]
	ADD AX,2
	MOV [bp+4], AX
	; Fin de las Sentencias
	MOV AX,[bp+4]
	pop bp
RET 2
ENDP
;Fin Funcion javi

;Inicio Procedimiento main
PROC main
	mov DX, sp; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
	amain DW 0
	bmain DW 0
	mov sp, DX; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
	
	; Comienzo de las Sentencias
	MOV AX, 0; Para evitar el arrastre de Basura
	MOV AX,1
	MOV amain, AX
	MOV AX,2
	MOV bmain, AX
	MOV AX,0
	MOV BX,0
	MOV DX,0
	MOV AX,amain
	call PRINT_NUM
	PRINTN ""
	MOV AX,0
	MOV BX,0
	MOV DX,0
	MOV AX,2
	MOV BX,3
	MUL BX
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
	MOV AX,4
	SUB AX,1
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
	
	; Muevo las Variables al Stack
	MOV BX, 0
	MOV BX, amain
	push BX
	MOV BX, 0
	MOV BX, bmain
	push BX
	; Muevo los Temporales al Stack
	MOV BX, 0
	MOV BX, temp1main
	push BX
	MOV BX, 0
	MOV BX, temp2main
	push BX
	; Llamo a la funcion
	MOV AX,amain
	push AX
	call javi
	MOV DX, AX ;Guardo el retorno en un Registro Seguro
	MOV CX, bp
	; Recupero los temporales del Stack
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV temp2main, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV temp1main, BX
	; Recupero las Variables del Stack
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV bmain, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV amain, BX
	; Fin del Recupero
	MOV AX, 0 ;
	MOV AX, DX ;Recupero el retorno
	
	MOV CX,sp ;Para evitar el corrimiento
	MOV BX, 0 ;Sino se comporta muy extrano
	MOV DX, AX ;Sino se comporta muy extrano
	temp3main DW 0
	MOV AX, 0 ;Arrastra basura la declaracion!!
	MOV AL, 0 ;Arrastra basura la declaracion!!
	MOV AH, 0 ;Arrastra basura la declaracion!!
	MOV AX, DX ;Sino se comporta muy extrano
	MOV sp,CX ;Recupero el corrimiento
	MOV temp3main,AX
	MOV AX,temp2main
	MOV BX,temp3main
	MUL BX
	MOV temp2main,AX
	MOV AX,temp1main
	ADD AX,temp2main
	MOV amain, AX
	MOV AX,0
	MOV BX,0
	MOV DX,0
	MOV AX,amain
	call PRINT_NUM
	PRINTN ""
	MOV AX,0
	MOV BX,0
	MOV DX,0
	; Fin de las Sentencias
RET
ENDP
;Fin Procedimiento main

programa ENDS
