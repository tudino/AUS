#!/bin/bash

# a
tres_numeros_seguidos() {
    COUNT=0
    while [[ $# -gt 0 ]]; do
        if [[ "$1" =~ ^-?[0-9]+$ ]]; then
            ((COUNT++))
            # echo $1
        else
            COUNT=0
        fi
        shift
        if [[ $COUNT -eq 3 ]]; then
            return 0
        fi
    done
    return 1
}

echo "A) 3 numeros seguidos"
while read -r line; do
    # echo $line
    if tres_numeros_seguidos $line; then
        echo $line
    fi
done < "archivo.txt"


# b