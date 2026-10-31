#!/bin/bash

[[ $# < 1 ]] && echo "debe ingresar args" && exit 0

CANT=0

for arg in "$@"; do
    ((CANT++))
done

(($CANT % 2 == 0))  && echo "Cantidad PAR $CANT" && exit 0

echo "Cantidad IMPAR $CANT"
