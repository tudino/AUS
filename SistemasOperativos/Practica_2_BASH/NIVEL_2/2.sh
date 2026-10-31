#!/bin/bash

DIR=$1

[ ! -d $DIR ] && echo "No es directorio" && exit 0

SH=0
TXT=0

for file in $(ls $DIR); do
    case "${file##*.}" in
        txt) ((TXT++)) ;;
        sh) ((SH++)) ;;
        *) ;;
    esac

    if [[ "${file:0:2}" == [a-z][a-z] ]]; then
        echo "[$file] Comienza con dos letras minúsculas"
    fi
done

echo "$TXT (txt) - $SH (sh)"