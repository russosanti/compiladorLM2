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
n DW 0
s DW 0
a DW 0,0,0,0,0,0,0,0,0,0,0,0
c DW 0,0,0,0,0
r DW 0
; Finaliza la delcaracion de Globales

;Inicio Procedimiento proc1
PROC proc1
	aproc1 DW 0
	bproc1 DW 0
	
	; Comienzo de las Sentencias
	MOV AX,1
	MOV r, AX
	MOV AX,3
	MOV BX, AX
	CMP BX,5
	JL Less1806856794;
	PRINTN "Index out of bounds" 
	MOV AH, 4Ch
	INT 21h
	Less1806856794:
	MOV AX,c[BX]
	NOT AX
	MOV BX, 0 ;Sino se comporta muy extrano
	temp1proc1 DW 0
	MOV temp1proc1,AX
	MOV AX,n
	CMP AX,aproc1
	JL Less275884531;
	MOV AX,0
	JMP Fin275884531;
	Less275884531:
	MOV AX,1
	Fin275884531:
	MOV BX, 0 ;Sino se comporta muy extrano
	temp2proc1 DW 0
	MOV temp2proc1,AX
	MOV AX,s
	CMP AX,r
	JNE Ineq374118644;
	MOV AX,0
	JMP Fin374118644;
	Ineq374118644:
	MOV AX,1
	Fin374118644:
	MOV BX, 0 ;Sino se comporta muy extrano
	temp3proc1 DW 0
	MOV temp3proc1,AX
	MOV AX,temp2proc1
	OR AX,temp3proc1
	MOV temp2proc1,AX
	MOV AX,temp1proc1
	AND AX,temp2proc1
	MOV bproc1, AX
	MOV AX,1
	MOV temp1proc1,AX
	MOV AX,1
	MOV BX, AX
	CMP BX,5
	JL Less2127414939;
	PRINTN "Index out of bounds" 
	MOV AH, 4Ch
	INT 21h
	Less2127414939:
	MOV AX, temp1proc1
	MOV c[BX], AX
	While2007640870:
	MOV AX,bproc1
	cmp ax,1
	jne EndWhile2007640870;
	MOV AX,aproc1
	CMP AX,10
	JE Equal1157040426;
	JL Less1157040426;
	JG Greater1157040426;
	Less1157040426:
	MOV AX,1
	JMP Fin1157040426:
	Equal1157040426:
	MOV AX,1
	JMP Fin1157040426:
	Greater1157040426:
	MOV AX,0
	Fin1157040426:
	cmp ax,1
	jne ElseBlk914016197;
	MOV AX,aproc1
	ADD AX,1
	MOV aproc1, AX
	jmp EndOfIf919695988;
	ElseBlk914016197:
	EndOfIf919695988:
	PRINT "'Visualizacion'"
	MOV AX,bproc1
	call PRINT_NUM
	PRINT "' '"
	MOV AX,aproc1
	call PRINT_NUM
	PRINTN ""
	MOV AX,3
	MOV BX, AX
	CMP BX,5
	JL Less1890662857;
	PRINTN "Index out of bounds" 
	MOV AH, 4Ch
	INT 21h
	Less1890662857:
	MOV AX,c[BX]
	MOV temp1proc1,AX
	MOV AX,aproc1
	ADD AX,n
	MOV temp2proc1,AX
	MOV AX,temp2proc1
	CMP AX,r
	JL Less55112940;
	MOV AX,0
	JMP Fin55112940;
	Less55112940:
	MOV AX,1
	Fin55112940:
	MOV temp2proc1,AX
	MOV AX,temp1proc1
	AND AX,temp2proc1
	MOV bproc1, AX
	jmp While2007640870;
	EndWhile2007640870:
	; Fin de las Sentencias
RET
ENDP
;Fin Procedimiento proc1

;Inicio Procedimiento proc2
PROC proc2
	push bp
	mov bp, sp
	
	w11proc2 DW 0
	w12proc2 DW 0
	qproc2 DW 0
	
	; Comienzo de las Sentencias
	MOV AX,0
	MOV BX, 0 ;Sino se comporta muy extrano
	temp1proc2 DW 0
	MOV temp1proc2,AX
	MOV AX,2
	MOV BX, AX
	CMP BX,5
	JL Less717730723;
	PRINTN "Index out of bounds" 
	MOV AH, 4Ch
	INT 21h
	Less717730723:
	MOV AX, temp1proc2
	MOV c[BX], AX
	MOV AX,s
	ADD AX,7
	MOV BX,2
	MUL BX
	MOV s, AX
	MOV AX,1
	MOV qproc2, AX
	call SCAN_NUM
	mov w11proc2,cx
	MOV AX,w11proc2
	MOV BX,2
	MUL BX
	MOV temp1proc2,AX
	MOV AX,temp1proc2
	ADD AX,s
	MOV w12proc2, AX
	While484616262:
	MOV AX,w12proc2
	SUB AX,2
	MOV temp1proc2,AX
	MOV BX, [bp+4]
	MOV AX,word ptr [BX]
	ADD AX,s
	MOV BX, 0 ;Sino se comporta muy extrano
	temp2proc2 DW 0
	MOV temp2proc2,AX
	MOV AX,temp1proc2
	CMP AX,temp2proc2
	JE Equal1687526010;
	JL Less1687526010;
	JG Greater1687526010;
	Less1687526010:
	MOV AX,1
	JMP Fin1687526010:
	Equal1687526010:
	MOV AX,1
	JMP Fin1687526010:
	Greater1687526010:
	MOV AX,0
	Fin1687526010:
	cmp ax,1
	jne EndWhile484616262;
	MOV AX,w12proc2
	MOV BX,2
	MUL BX
	MOV w12proc2, AX
	jmp While484616262;
	EndWhile484616262:
	While433124141:
	MOV AX,w12proc2
	ADD AX,7
	MOV temp1proc2,AX
	MOV AX,w11proc2
	MOV BX,2
	DIV BX
	MOV temp2proc2,AX
	MOV AX,temp1proc2
	CMP AX,temp2proc2
	JG Greater1695358104;
	MOV AX,0
	JMP Fin1695358104;
	Greater1695358104:
	MOV AX,1
	Fin1695358104:
	cmp ax,1
	jne EndWhile433124141;
	MOV AX,qproc2
	MOV BX,2
	MUL BX
	MOV qproc2, AX
	MOV AX,w12proc2
	MOV BX,2
	DIV BX
	MOV w12proc2, AX
	MOV BX, [bp+4]
	MOV AX,w12proc2
	CMP AX,word ptr [BX]
	JE Equal1070625532;
	JL Less1070625532;
	JG Greater1070625532;
	Less1070625532:
	MOV AX,1
	JMP Fin1070625532:
	Equal1070625532:
	MOV AX,1
	JMP Fin1070625532:
	Greater1070625532:
	MOV AX,0
	Fin1070625532:
	cmp ax,1
	jne ElseBlk237838111;
	MOV BX, [bp+4]
	MOV AX,word ptr [BX]
	SUB AX,w12proc2
	MOV BX, [bp+4]
	MOV word ptr [BX], AX
	MOV AX,qproc2
	ADD AX,1
	MOV qproc2, AX
	jmp EndOfIf878064510;
	ElseBlk237838111:
	EndOfIf878064510:
	jmp While433124141;
	EndWhile433124141:
	MOV AX,qproc2
	MOV temp1proc2,AX
	MOV AX,1
	MOV BX, AX
	CMP BX,12
	JL Less122597386;
	PRINTN "Index out of bounds" 
	MOV AH, 4Ch
	INT 21h
	Less122597386:
	MOV AX, temp1proc2
	MOV a[BX], AX
	; Fin de las Sentencias
	pop bp
RET 2
ENDP
;Fin Procedimiento proc2

;Inicio Funcion fun1
PROC fun1
	push bp
	mov bp, sp
	
	nfun1 DW 0
	
	; Comienzo de las Sentencias
	MOV AX,[bp+6]
	CMP AX,0
	JG Greater1057449029;
	MOV AX,0
	JMP Fin1057449029;
	Greater1057449029:
	MOV AX,1
	Fin1057449029:
	cmp ax,1
	jne ElseBlk2118651478;
	MOV AX,-45
	MOV nfun1, AX
	jmp EndOfIf749039839;
	ElseBlk2118651478:
	MOV AX,70
	MOV nfun1, AX
	EndOfIf749039839:
	; Fin de las Sentencias
	MOV AX,nfun1
	MOV BX,2
	MUL BX
	CMP AX,0
	JE Equal563435359;
	JL Less563435359;
	JG Greater563435359;
	Less563435359:
	MOV AX,0
	JMP Fin563435359:
	Equal563435359:
	MOV AX,1
	JMP Fin563435359;
	Greater563435359:
	MOV AX,1
	Fin563435359:
	MOV BX, 0 ;Sino se comporta muy extrano
	temp1fun1 DW 0
	MOV temp1fun1,AX
	MOV AX,[bp+4]
	CMP AX,0
	JE Eq1402679090;
	MOV AX,0
	JMP Fin1402679090;
	Eq1402679090:
	MOV AX,1
	Fin1402679090:
	NOT AX
	MOV BX, 0 ;Sino se comporta muy extrano
	temp2fun1 DW 0
	MOV temp2fun1,AX
	MOV AX,temp1fun1
	OR AX,temp2fun1
	pop bp
RET 4
ENDP
;Fin Funcion fun1

;Inicio Procedimiento main
PROC main
	xmain DW 0
	
	; Comienzo de las Sentencias
	call proc1
	MOV AX,7
	ADD AX,1
	MOV s, AX
	MOV AX,s
	LEA AX,s
	push AX
	call proc2
	MOV AX,5
	push AX
	MOV AX,8
	push AX
	call fun1
	MOV xmain, AX
	MOV AX,xmain
	MOV BX, 0 ;Sino se comporta muy extrano
	temp1main DW 0
	MOV temp1main,AX
	MOV AX,4
	MOV BX, AX
	CMP BX,5
	JL Less1899472511;
	PRINTN "Index out of bounds" 
	MOV AH, 4Ch
	INT 21h
	Less1899472511:
	MOV AX, temp1main
	MOV c[BX], AX
	MOV AX,s
	ADD AX,1
	call PRINT_NUM
	PRINTN ""
	MOV AX,s
	ADD AX,1
	MOV BX, AX
	CMP BX,12
	JL Less2090079722;
	PRINTN "Index out of bounds" 
	MOV AH, 4Ch
	INT 21h
	Less2090079722:
	MOV AX,a[BX]
	call PRINT_NUM
	; Fin de las Sentencias
RET
ENDP
;Fin Procedimiento main

programa ENDS
