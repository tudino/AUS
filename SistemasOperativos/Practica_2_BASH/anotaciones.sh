#!/bin/bash

#
# Dar permiso de ejecucion a los archivos en las PC del Lab
# chmod +x <file>
#

# TEXTO -> $TEXTO	--> variable
# $1 $2 .. $n		--> son los argumentos de entrada
# read 			--> espera una entrada de teclado

# 0 -> STDIN		--> entrada
# 1 -> STDOUT		--> salida	1>
# 2 -> STDERROR		--> salida err	2>

# Corchetes				--> expresion condicion, se usa en los IF
# [ $A -gt $B ] && [ $B -gt $C ]	--> no permite AND dentro
# [[ $A -lt $B && $B -lt $C ]]		--> permite el AND dentro 
# [[ $# != n ]] && exit 1		--> termina si la cantidad de argumentos es distinta de n
# DATE=$(date -d 13/01/2026)
# [[ $? != 0 ]] && exit 1		--> termina si el exit status de date es != 0

# if [ condicion ] 
# then 
#	sentencia 
# fi

# IF INLINE
# [ $A -gt $B ] && echo A mayor que B

# case $VAR in
#	regexp1)
#	;;	--> break
#
#	regexp2)
#	;;	--> break
#
#	*)	--> default
#	;;	--> break
# esac

# for i in seq 1 5
# do
#
#	echo i
#
# done


# while [[ ___ ]]
# do
#	comandos
# done
