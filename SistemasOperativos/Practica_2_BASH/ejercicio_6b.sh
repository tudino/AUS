#!/bin/bash

FILE_PW="passwd.txt"

[ ! -f $FILE_PW ] && echo "Ha ocurrido un problema" && exit 0

echo "Login:"
read -p "Ingrese el usuario: " USER
read -s -p "Ingrese la contraseña: " PASS
echo 

LINE=$(grep -w "$USER" $FILE_PW)

[ $? != 0 ] && echo "Login invalido" && exit 0

USUARIO=$(echo $LINE | cut -d ':' -f 1)
CLAVE=$(echo $LINE | cut -d ':' -f 2)


[[ $USUARIO == $USER && $CLAVE == $PASS ]] && echo "Login exitoso" && exit 0

echo
echo "Login invalido"
