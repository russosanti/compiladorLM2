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

