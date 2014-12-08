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

;Inicio Funcion hanoi
PROC hanoi
	push bp
	mov bp, sp
	
	mov DX, sp; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
	rhanoi DW 0
	ihanoi DW 0
	mov sp, DX; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
	
	; Comienzo de las Sentencias
	MOV AX, 0; Para evitar el arrastre de Basura
	MOV AX,[bp+4]
	CMP AX,1
	JE Eq111141245;
	MOV AX,0
	JMP Fin111141245;
	Eq111141245:
	MOV AX,1
	Fin111141245:
	cmp ax,1
	jne ElseBlk1787615472;
	MOV AX,1
	MOV rhanoi, AX
	jmp EndOfIf1157016374;
	ElseBlk1787615472:
	
	; Muevo las Variables al Stack
	MOV BX, 0
	MOV BX, rhanoi
	push BX
	MOV BX, 0
	MOV BX, ihanoi
	push BX
	; Muevo los Temporales al Stack
	; Llamo a la funcion
	MOV AX,[bp+4]
	SUB AX,1
	push AX
	call hanoi
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
	MOV ihanoi, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV rhanoi, BX
	; Fin del Recupero
	MOV AX, 0 ;
	MOV AX, DX ;Recupero el retorno
	
	MOV ihanoi, AX
	MOV AX,2
	MOV BX,ihanoi
	MUL BX
	ADD AX,1
	MOV rhanoi, AX
	EndOfIf1157016374:
	; Fin de las Sentencias
	MOV AX,rhanoi
	pop bp
RET 2
ENDP
;Fin Funcion hanoi

;Inicio Procedimiento main
PROC main
	mov DX, sp; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
	discosmain DW 0
	resmain DW 0
	mov sp, DX; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
	
	; Comienzo de las Sentencias
	MOV AX, 0; Para evitar el arrastre de Basura
	MOV AX,0
	MOV BX,0
	MOV DX,0
	PRINT "'Numero de discos'"
	PRINTN ""
	MOV AX,0
	MOV BX,0
	MOV DX,0
	call SCAN_NUM
	mov discosmain,cx
	PRINTN ""
	MOV AX,0
	MOV BX,0
	MOV DX,0
	PRINT "'Movimientos necesarios'"
	PRINTN ""
	MOV AX,0
	MOV BX,0
	MOV DX,0
	
	; Muevo las Variables al Stack
	MOV BX, 0
	MOV BX, discosmain
	push BX
	MOV BX, 0
	MOV BX, resmain
	push BX
	; Muevo los Temporales al Stack
	; Llamo a la funcion
	MOV AX,discosmain
	push AX
	call hanoi
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
	MOV resmain, BX
	MOV bp, 0
	MOV bp, sp
	MOV BX, [bp]
	pop [bp]
	MOV bp, 0
	MOV bp, sp ;Por errores internos del Emu
	MOV discosmain, BX
	; Fin del Recupero
	MOV AX, 0 ;
	MOV AX, DX ;Recupero el retorno
	
	MOV resmain, AX
	MOV AX,0
	MOV BX,0
	MOV DX,0
	MOV AX,resmain
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
