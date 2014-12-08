include emu8086.inc

programa SEGMENT
ASSUME CS:programa, DS:programa, ES:programa, SS:programa
ORG 0100h

DEFINE_PRINT_NUM
DEFINE_PRINT_NUM_UNS

JMP inicio

inicio:
call main

MOV AH, 4Ch
INT 21h



; ***Comienzo rutina readln***
;
; Obtiene por teclado un numero (word con o sin signo)
; (usa rutina writeSTR, notar que posee dos macros)
; Solamente permite ingresar caracteres <0>..<9>, <->, <Backspace>, <Enter> (cuando corresponden)
; No realiza control de overflow, ni permite <Control><Break> para cortar la ejecucion del programa
;
; Parametro entrada: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador)
; Parametro salida: numero obtenido (word/via pop del llamador)
; (el llamador antes debera efectuar un push de un word para que esta rutina deje el resultado ahi)
;
dig macro digbase
    cmp al, digbase
    jl inicioread
    cmp al, '9'
    jg inicioread
    mov ah, 0Eh
    int 10h     
    mov [bp-1], 03h
    mov cl, al
    sub cl, 48
    mov ax, si
    mov bx, 000Ah
    mul bx           ; AX = AX * 10
    add ax, cx       ; AX = AX + CX (digito)
    mov si, ax
endm
writeBS macro
    mov ah, 0Eh
    int 10h
    mov al, ' '
    int 10h
    mov al, 08h
    int 10h     
endm
read proc near
    push bp
    mov bp, sp
    sub sp, 1        ; [bp-1] = estado (00h=inicio, 01h=solo 0, 02h=-, 03h=digitos)
    sub sp, 1        ; [bp-2] = signo (00h=positivo, 01h=negativo)

    push ax
    push bx
    push cx
    push dx
    push si
    mov [bp-1], 00h
    mov [bp-2], 00h
    mov si, 0000h    ; valor
    mov bx, 0
    mov cx, 0

inicioread:
    mov ah, 0
    int 16h
    
    cmp [bp-1], 00h
    je estado0
    cmp [bp-1], 01h
    je estado1
    cmp [bp-1], 02h
    je estado2
    cmp [bp-1], 03h
    je estado3
    
estado0:    
    cmp al, 0Dh
    je inicioread
    cmp al, '0'
    jne estado0a
    mov [bp-1], 01h
    mov ah, 0Eh
    int 10h     
    jmp inicioread
estado0a:
    cmp al, '-'
    jne estado0b
    cmp [bp+4], 0000h
    je inicioread    
    mov [bp-1], 02h
    mov [bp-2], 01h
    mov ah, 0Eh
    int 10h     
    jmp inicioread
estado0b:
    dig '1'
    jmp inicioread

estado1:    
    cmp al, 0Dh
    je finread
    cmp al, 08h
    jne inicioread
    writeBS
    mov [bp-1], 00h
    jmp inicioread

estado2:
    cmp al, 0Dh
    je inicioread
    cmp al, 08h
    jne estado2a
    writeBS
    mov [bp-1], 00h
    mov [bp-2], 00h
    jmp inicioread
estado2a:    
    dig '1'
    jmp inicioread

estado3:    
    cmp al, 0Dh
    je finread
    cmp al, 08h
    jne estado3a
    writeBS
    mov ax, si
    mov dx, 0
    mov bx, 000Ah
    div bx           ; AX = AX / 10
    mov si, ax
    cmp si, 0
    jne inicioread
    cmp [bp-2], 00h
    jne estado3bs1
    mov [bp-1], 00h
    jmp inicioread
estado3bs1:
    mov [bp-1], 02h
    jmp inicioread
estado3a:    
    dig '0'
    jmp inicioread

finread:
    cmp [bp-2], 00h
    je finread2
    neg si
finread2:
    mov [bp+6], si
    pop si
    pop dx
    pop cx
    pop bx
    pop ax
    mov sp, bp   
    pop bp    
    ret 2
read endp
;
; ***Fin rutina readln***

; Comienza la delcaracion de Var globales
; Finaliza la delcaracion de Globales

;Inicio Procedimiento main
PROC main
	
	; Comienzo de las Sentencias
	PRINT "'Hola Mundo'"
	PRINTN ""
	PRINT "'TEST'"
	PRINT "'Soy Santi come pibitos, gato y puto'"
	PRINTN ""
	; Fin de las Sentencias
RET
ENDP
;Fin Procedimiento main

programa ENDS
