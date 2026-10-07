#!/bin/bash

# recibe dia mes anio
# detectar que dia de la semana es (uso date)

D=$1
M=$2
A=$3

[[ $# != 3 ]] && echo "Args validos DD MM YYYY" && exit 1;

FECHA=$M/$D/$A

# FORMA 1
DIA=$(date -d $FECHA +%u 2> /dev/null)

[[ $? != 0 ]] && echo "Fecha invalida" && exit 1;

case $DIA in
	1) echo lunes 
		;;
	2) echo martes 
		;;
	3) echo miercoles
		;;
	4) echo jueves
		;;
	5) echo viernes
		;;
	6) echo sabado
		;;
	7) echo domingo
		;;
#	*) echo osvaldo 
#		;;
esac;



# FORMA 2
DIA=$(date -d $FECHA +%A)
echo $DIA
