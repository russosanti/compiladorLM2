org 100h

jmp comienzo

primera  db "Primera cadena"
segunda  db "Cadena siguiente a la primera"
textoent db "Ingrese numero elegido: "
textosal db "El numero elegido ingresado fue "

; ***Ejemplo de prueba que usa a todas las rutinas***
;
comienzo:
    push offset primera
    push 14
    call writeSTR

    push offset segunda
    push 29
    call writeSTR

    call writeCRLF

    push 0001h
    push 31005
    call writeNUM

    call writeCRLF    

    push 0000h
    push 65280
    call writeNUM
    call writeCRLF    

    push 0001h
    push 65280
    call writeNUM
    call writeCRLF    

    push 0001h
    push -4526
    call writeNUM
    call writeCRLF    

    push 0000h
    push -4526
    call writeNUM
    call writeCRLF    

    push offset textoent
    push 24
    call writeSTR

    push 0000h      ; hay que efectuar un push de un word cualquiera (solamente para dejar espacio en el stack)
    push 0001h      ; aritmetica con signo
    call readln
    pop ax          ; obtiene el numero ingresado en este caso en el registro AX

    call writeCRLF
    
    call writeCRLF
    
    push offset textosal
    push 32
    call writeSTR

    push 0001h
    push ax         ; el mismo contenido que el numero ingresado
    call writeNUM
    
    call writeCRLF

    mov ax, 4C00h   ; salida del programa (al sistema operativo)
    int 21h         ;
;
; ***Fin ejemplo de prueba que usa todas las rutinas***
    

    
; ***Comienzo rutina writeCRLF***
;
; Imprime por pantalla un caracter <Enter> (<CR><LF>)
;
; Parametros: -
;
writeCRLF proc near
    push ax
    
    mov al, 0Dh
    mov ah, 0Eh
    int 10h     

    mov al, 0Ah
    mov ah, 0Eh
    int 10h     

    pop ax
    ret
writeCRLF endp
;
; ***Fin rutina writeCRLF***



; ***Comienzo rutina writeSTR***
;
; Imprime por pantalla un string sin <Enter> al final
;
; Parametro entrada 1: direccion de memoria donde comienza el string a imprimir (word/via push del llamador)
; Parametro entrada 2: cantidad de caracteres del string (word/via push del llamador)
;
writeSTR proc near
    push bp
    mov bp, sp
    push ax
    push bx
    push cx
    push si

    mov si, [bp+6]  ; a SI se le asigna el primer parametro (direccion)
    mov cx, [bp+4]  ; a CX se le asigna el segundo parametro (cantidad de caracteres)
    xor bx, bx
    
loop:    
    mov al, [si]
    mov ah, 0Eh
    int 10h     
    inc bx
    inc si
    cmp bx, cx
    jne loop

    pop si
    pop cx
    pop bx
    pop ax
    pop bp
    ret 4
writeSTR endp
;
; ***Fin rutina writeSTR***



; ***Comienzo rutina writeNUM***
;
; Imprime por pantalla un numero (word con signo) sin <Enter> al final
; (usa rutina writeSTR)
;
; Parametro entrada 1: tipo de aritmetica -0000h=sin signo, 0001h=con signo- (word/via push del llamador)
; Parametro entrada 2: numero a imprimir (word/via push del llamador)
;
writeNUM proc near
    push bp
    mov bp, sp
    sub sp, 1        ; "variable local" [bp-1] = signo (00h=positivo, 01h=negativo)
    sub sp, 6        ; "variable local" [bp-7] = espacio para imprimir (db 6 dup(?))

    push ax
    push bx
    push cx
    push dx
    push si
    
    mov [bp-1], 00h
    mov ax, [bp+4]
    
    cmp [bp+6], 0
    je comenzar      ; Si no es aritmetica con signo, comienza
    cmp ax, 0
    jge comenzar     ; Si no es negativo, comienza

    neg ax           ; Realiza ax = -ax
    mov [bp-1], 01h
    
comenzar:
    mov bx, 10
    mov cx, 0
    mov si, bp
    sub si, 8

proxdiv:
    dec si
    xor dx, dx    
    div bx
    add dl, 48
    mov [si], dl
    inc cx
    cmp ax, 0
    jnz proxdiv
    
    cmp [bp-1], 00h
    jz mostrar
    
    dec si
    mov [si], '-'
    inc cx

mostrar:    
    push si
    push cx
    call writeSTR

    pop si
    pop dx
    pop cx
    pop bx
    pop ax

    mov sp, bp   
    pop bp
    ret 4
writeNUM endp
;
; ***Fin rutina writeNUM***



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
readln proc near
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
readln endp
;
; ***Fin rutina readln***

ends

end start
