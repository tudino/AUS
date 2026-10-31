#!/bin/bash

[[ $# != 1 ]] && echo "1 directorio" && exit 0

[[ ! -d $1 ]] && echo "directorio invalido" && exit 0

[[ ! -r $1 ]] && echo "sin permiso de lectura" && exit 0

FILES=0
DIRS=0
LINES=0
TMP=0

for entrada in $(ls $1); do
    [ -d "$entrada" ] && (($DIRS++))

    if [ -f "$entrada" ]; then
        TMP=$(wc -l < "$entrada")
        ((LINES+=$TMP))
        ((FILES++))
    fi
done

echo "$DIRS (directorios) ; $FILES (archivos) ; $LINES (lineas)"
