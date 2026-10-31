#!/bin/bash

COUNT=0
SUMA=0

while [[ $# -gt 0 && $COUNT -le 10 ]]; do
    if [[ $1 =~ ^-?[0-9]+$ ]]; then
        ((COUNT++))
        ((SUMA+=$1))
    fi
    shift
done

[[ $COUNT -gt 10 ]] && echo "Ha superado los 10 numeros" && exit 0

echo $SUMA $COUNT
