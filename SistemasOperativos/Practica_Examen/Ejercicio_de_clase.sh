#!/bin/bash

[[ $# < 1 ]] && echo "Debe ingresar al menos 1 arg" && exit 0

FLONG="long.txt"
FSHORT="short.txt"

for arg in "$@"; do
	
	#if [[ $(echo -n "$arg" | wc -m) > 10 ]]
	if [[ ${#arg} -gt 10 ]];
	then
		echo "$arg" >> $FLONG
	else
		echo "$arg" >> $FSHORT
	fi
done

