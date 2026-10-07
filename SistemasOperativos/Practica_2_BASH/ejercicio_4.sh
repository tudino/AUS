#!/bin/bash

[ $# != 2 ] && echo "Debe ingresar <nombre_comprimido> <nombre_carpeta>" && exit 0

FILENAME=$1
DIR=$2

[ ! -d $DIR ] && echo "El directorio ingresado no existe" && exit 0

ZIPPED="$FILENAME.tar.bz2"
RESULT=$(tar -cjf $ZIPPED $DIR)

[ $? != 0 ] && echo "Algo salio mal" && exit 0

ZIPPED_SIZE=$(du -h $ZIPPED)
DIR_SIZE=$(du -sh $DIR)

echo "Zipped size: $ZIPPED_SIZE"
echo "Dir size: $DIR_SIZE"