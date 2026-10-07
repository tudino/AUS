#!/bin/bash

# recibe dia mes anio
# retornar la cantidad de días transcurridos hasta la fecha (uso date)

D=$1
M=$2
A=$3

[[ $# != 3 ]] && echo "Args validos DD MM YYYY" && exit 1;

FECHA=$M/$D/$A

[[ $? != 0 ]] && echo "Fecha invalida" && exit 1;

# Obtengo segundos desde 1970
FECHA_SINCE=$(date -d $FECHA +%s)
HOY_SINCE=$(date +%s)

DIFF_MS=$((HOY_SINCE - FECHA_SINCE))
DIFF_DIAS=$((DIFF_MS / 60 / 60 / 24))
echo $DIFF_DIAS