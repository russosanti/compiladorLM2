programa SEGMENT
ASSUME CS:programa, DS:programa, ES:programa, SS:programa
ORG 0100h

JMP inicio

...

inicio:

...
MOV AH, 4Ch
INT 21h

programa ENDS

