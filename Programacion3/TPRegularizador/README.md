# Trabajo Practico Regulizador - 

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


## Solucion

1. **Recorrido de directorios:** el hilo principal recorre el directorio raíz y sus subdirectorios, identificando los archivos contenidos en cada uno.
2. **Generación de tareas:** los archivos de cada directorio se dividen en grupos de hasta 10 archivos. Cada grupo representa una tarea independiente.
3. **Distribución de tareas:** las tareas se distribuyen entre los hilos disponibles, procurando equilibrar la cantidad de archivos asignados a cada uno según el tamaño de los directorios.
4. **Búsqueda concurrente:** cada hilo ejecuta sus tareas, comparando el nombre de cada archivo con el nombre buscado. No se lee el contenido de los archivos.
5. **Resultados:** cuando se encuentra una coincidencia, se registra la ruta absoluta del archivo y el nombre del hilo que lo encontró.
6. **Finalización:** el hilo principal espera a que todos los hilos terminen su trabajo y reúne los resultados obtenidos.


