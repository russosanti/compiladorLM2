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
	MOV CX, bp
	MOV bp, sp
	ADD AX,2
	MOV bp, CX
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
	MOV CX, bp
	MOV bp, sp
	MOV BX,3
	MUL BX
	MOV bp, CX
	MOV CX, bp
	MOV bp, sp
	MOV [bp-2],AX
	MOV bp, CX
	MOV AX,4
	MOV CX, bp
	MOV bp, sp
	SUB AX,1
	MOV bp, CX
	MOV CX, bp
	MOV bp, sp
	MOV [bp-4],AX
	MOV bp, CX
	; Muevo los Temporales al Stack
	MOV bp, sp
	MOV CX, bp
	MOV BX, 0
	MOV BX, [bp-2]
	push BX
	MOV bp, sp
	MOV BX, 0
	MOV BX, [bp-2]
	push BX
	MOV bp, sp
	push CX
	MOV bp, CX
	
	; Muevo las Variables al Stack
	MOV BX, 0
	MOV BX, amain
	push BX
	MOV BX, 0
	MOV BX, bmain
	push BX
	; Llamo a la funcion
	MOV AX,amain
	push AX
	call javi
	MOV DX, AX ;Guardo el retorno en un Registro Seguro
	MOV CX, bp
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
	pop CX
	MOV bp, CX
	; Recupero los temporales del Stack
	MOV sp, bp
	; Fin del Recupero
	MOV AX, 0 ;
	MOV AX, DX ;Recupero el retorno
	
	MOV [bp-6],AX
	MOV CX, bp
	MOV bp, sp
	MOV AX,[bp-4]
	MOV BX,[bp-6]
	MUL BX
	MOV bp, CX
	MOV CX, bp
	MOV bp, sp
	MOV [bp-4],AX
	MOV bp, CX
	MOV CX, bp
	MOV bp, sp
	MOV AX,[bp-2]
	ADD AX,[bp-4]
	MOV bp, CX
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
