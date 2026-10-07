#!/bin/bash

[ $# == 0 ] && echo "No hay argumentos" && exit 0

FILE=$1

# FORMA 1 -> tac
# INVERTED=$(tac $FILE)
# echo $INVERTED && exit 0

# FORMA 2 -> bucle + sed (retorna una linea dado el nro de linea)
LINES_NO=$(wc -l < $FILE)

while [ $LINES_NO -gt 0 ]
do
    LINE=$(sed -n "${LINES_NO}p" $FILE)
    echo $LINE
    ((LINES_NO--))
done
