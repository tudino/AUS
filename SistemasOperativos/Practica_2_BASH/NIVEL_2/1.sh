#!/bin/bash

[[ $# != 2 ]] && echo "debe 2 args" && exit 0

for archivo in "$@"; do
    [[ -f "$archivo" && -r "$archivo" ]] || {
        echo "Archivo inválido: $archivo"
        exit 1
    }
done

COUNT1=$(cat "$1" | wc -l)
COUNT2=$(cat "$2" | wc -l)

[[ $COUNT1 -eq $COUNT2 ]] && echo "Igual cant lineas" && exit 0

echo "Diferente cant lineas"