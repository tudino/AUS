#!/bin/bash

[ $# != 3 ] && echo "Debe ingresar <directorio> <extension> <patron_renombre>" && exit 0

# echo "Ingrese el directorio"
# read DIR
DIR=$1

[ ! -d $DIR ] && echo "El directorio <$DIR> no existe" && exit 0

# echo "Ingrese la extension"
# read EXT
EXT=$2

# echo "Ingrese el patron de renombre"
# read PAT
PAT=$3

C=1
for path in "$DIR"*"$EXT"; do
    BASE=$(dirname "$path")
    RENAMED="$BASE/$PAT$C$EXT"
    RESULT=$(mv "$path" $RENAMED)
    [ $? == 0 ] && echo "$path renombrado a $RENAMED"
    ((C++))
done