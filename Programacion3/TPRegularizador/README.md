# Trabajo Practico Regulizador

## Buscador de archivos multihilo - E/S + Hilos

Crear un programa de busqueda que encuentre todas las apariciones de un archivo determinado dentro de un directorio y sus subdirectorios, utilizando una cantidad configurable de hilos.


## Parámetros de entrada

- **Directorio:** ruta del directorio donde comienza la búsqueda.
- **Nombre del archivo:** nombre exacto del archivo que se desea encontrar.
- **Cantidad de hilos:** número de hilos que realizarán la búsqueda.


## Resultado

Se muestran todas las coincidencias encontradas, indicando para cada una:
- Ruta absoluta del archivo.
- Nombre del hilo que encontró el archivo.



## Utilidad generar_directorio.py para test

El script `generar_directorio.py` crea una estructura de archivos aleatoria con multiples carpetas y archivos para poder testear el programa.
Crea el directorio raiz `testdis` y multiples archivos `archivo.txt` en diferentes subdirectorios.

