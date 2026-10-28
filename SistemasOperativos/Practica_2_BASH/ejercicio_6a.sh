#!/bin/bash

[ $# != 2 ] && echo "Argumentos invalidos: <usuarios.txt> <claves.txt>" && exit 0

FILE_USER=$1
FILE_PASS=$2

OUTPUT_FILE="passwd.txt"

[ ! -f $OUTPUT_FILE ] && touch $OUTPUT_FILE

> $OUTPUT_FILE

[ ! -f $FILE_USER ] && echo "archivo usuarios.txt invalido" && exit 0
[ ! -f $FILE_PASS ] && echo "archivo claves.txt invalido" && exit 0

U=$(cat $FILE_USER | wc -l)
P=$(cat $FILE_PASS | wc -l)

[[ $U -ne $P ]] && echo "Los datos no coinciden" && exit 0

N=$U
for ((i=1; i<=N; i++)); do
    LINE_USER=$(head -n "$i" $FILE_USER | tail -n 1)
    LINE_PASS=$(head -n "$i" $FILE_PASS | tail -n 1)
    echo "$LINE_USER:$LINE_PASS" >> $OUTPUT_FILE
done

