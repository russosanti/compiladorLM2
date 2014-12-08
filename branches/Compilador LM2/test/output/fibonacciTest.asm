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

;Inicio Declaraciones fibonacci
mov DX, sp; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
fibfibonacci DW 0
naux1fibonacci DW 0
naux2fibonacci DW 0
afibonacci DW 0
bfibonacci DW 0
cfibonacci DW 0
mov sp, DX; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
;Fin Declaraciones fibonacci

;Inicio Declaraciones main
mov DX, sp; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
nromain DW 0
imain DW 0
rtamain DW 0
mov sp, DX; Evito el acarreo del sp cuando se declaran variables con mucha memoria ocupada
;Fin Declaraciones main

;Inicio Funcion fibonacci
PROC fibonacci
	push bp
	mov bp, sp
	
	; Comienzo de las Sentencias
	MOV AX, 0; Para evitar el arrastre de Basura
	MOV AX,[bp+4]
	MOV CX, bp
	MOV bp, sp
	CMP AX,3
	JL Less1149589687;
	MOV AX,0
	JMP Fin1149589687;
	Less1149589687:
	MOV AX,1
	Fin1149589687:
	MOV bp, CX
	cmp ax,1
	jne ElseBlk243497350;
	MOV AX,1
	MOV fibfibonacci, AX
	jmp EndOfIf1503613915;
	ElseBlk243497350:
	MOV AX,[bp+4]
	MOV CX, bp
	MOV bp, sp
	SUB AX,2
	MOV bp, CX
	MOV naux1fibonacci, AX
	MOV AX,[bp+4]
	MOV CX, bp
	MOV bp, sp
	SUB AX,1
	MOV bp, CX
	MOV naux2fibonacci, AX
	; Muevo los Temporales al Stack
	MOV bp, sp
	MOV CX, bp
	push CX
	MOV bp, CX
	
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
	; Llamo a la funcion
	MOV AX,naux1fibonacci
	push AX
	call fibonacci
	MOV DX, AX ;Guardo el retorno en un Registro Seguro
	MOV CX, bp
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
	pop CX
	MOV bp, CX
	; Recupero los temporales del Stack
	MOV sp, bp
	; Fin del Recupero
	MOV AX, 0 ;
	MOV AX, DX ;Recupero el retorno
	
	MOV afibonacci, AX
	; Muevo los Temporales al Stack
	MOV bp, sp
	MOV CX, bp
	push CX
	MOV bp, CX
	
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
	; Llamo a la funcion
	MOV AX,naux2fibonacci
	push AX
	call fibonacci
	MOV DX, AX ;Guardo el retorno en un Registro Seguro
	MOV CX, bp
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
	pop CX
	MOV bp, CX
	; Recupero los temporales del Stack
	MOV sp, bp
	; Fin del Recupero
	MOV AX, 0 ;
	MOV AX, DX ;Recupero el retorno
	
	MOV bfibonacci, AX
	MOV AX,afibonacci
	MOV CX, bp
	MOV bp, sp
	ADD AX,bfibonacci
	MOV bp, CX
	MOV fibfibonacci, AX
	EndOfIf1503613915:
	; Fin de las Sentencias
	MOV AX,fibfibonacci
	pop bp
RET 2
ENDP
;Fin Funcion fibonacci

;Inicio Procedimiento main
PROC main
	; Comienzo de las Sentencias
	MOV AX, 0; Para evitar el arrastre de Basura
	MOV AX,0
	MOV nromain, AX
	MOV AX,1
	MOV imain, AX
	While1798995156:
	MOV AX,nromain
	MOV CX, bp
	MOV bp, sp
	CMP AX,1
	JL Less1289320779;
	MOV AX,0
	JMP Fin1289320779;
	Less1289320779:
	MOV AX,1
	Fin1289320779:
	MOV bp, CX
	cmp ax,1
	jne EndWhile1798995156;
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
	jmp While1798995156;
	EndWhile1798995156:
	While1504334423:
	MOV AX,imain
	MOV [bp-2],AX
	MOV AX,nromain
	MOV CX, bp
	MOV bp, sp
	ADD AX,1
	MOV bp, CX
	MOV CX, bp
	MOV bp, sp
	MOV [bp-4],AX
	MOV bp, CX
	MOV CX, bp
	MOV bp, sp
	MOV AX,[bp-2]
	CMP AX,[bp-4]
	JL Less1023671230;
	MOV AX,0
	JMP Fin1023671230;
	Less1023671230:
	MOV AX,1
	Fin1023671230:
	MOV bp, CX
	cmp ax,1
	jne EndWhile1504334423;
	; Muevo los Temporales al Stack
	MOV bp, sp
	MOV CX, bp
	push CX
	MOV bp, CX
	
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
	; Llamo a la funcion
	MOV AX,imain
	push AX
	call fibonacci
	MOV DX, AX ;Guardo el retorno en un Registro Seguro
	MOV CX, bp
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
	pop CX
	MOV bp, CX
	; Recupero los temporales del Stack
	MOV sp, bp
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
	MOV CX, bp
	MOV bp, sp
	ADD AX,1
	MOV bp, CX
	MOV imain, AX
	jmp While1504334423;
	EndWhile1504334423:
	; Fin de las Sentencias
RET
ENDP
;Fin Procedimiento main

programa ENDS
