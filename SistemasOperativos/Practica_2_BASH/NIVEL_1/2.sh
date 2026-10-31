#!/bin/bash

[[ $# -ne 1 || $1 -le 0 ]] && echo "debe N entero positivo" && exit 0


for n in $(seq "$1"); do
    (($n % 2 != 0)) && echo $n
done
