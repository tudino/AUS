# Trabajo Practico Regulizador

E/S + Hilos

Crear un programa de busqueda que encuentre el nombre de un archivo dentro de un directorio, utilizando varios hilos.
Debe encontrar todas las apariciones del archivo y dar como resultado
- la ruta absoluta
- nombre del hilo que lo encontro

Los parametros de entrada deben ser:
- la ruta al directorio
- el nombre del archivo
- la cantidad de hilos a utilizar


## Solucion

El hilo principal auspicia de Maestro. Obtiene un listado con todos los directorios. El se encarga de buscar en el directorio raiz.

[raiz, nodo1-1, nodo1-2, nodo2-1, nodo3-1]

/raiz
    archivo1.txt
    archivo2.txt
    nodo1-1/
        archivo.txt
        archivo3.txt
    nodo1-2/
        archivo4.txt
        archivo.txt
        nodo2-1/
            archivo5.txt
            archivo.txt
            nodo3-1/
                archivo6.txt
                archivo.txt


En base a la cantidad de hilos disponibles decide cuantos nodos le asigna a cada hilo, o cuantos hilos a cada nodo