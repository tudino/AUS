"""
Crea directorio de test con subdirectorios y archivos al azar.
Crea entre 5 y 12 subdirectorios.
Genera 100 archivos.
Cada archivo tiene un 15 % de probabilidad de llamarse archivo.txt.
Los archivos se distribuyen aleatoriamente entre los directorios.
No garantiza que haya al menos un archivo.txt, porque la generación es aleatoria
"""
import os
import random
import shutil

raiz = "testdir"

if os.path.exists(raiz):
    shutil.rmtree(raiz)

os.makedirs(raiz)

nombres = ["documento", "imagen", "datos", "informe", "prueba", "archivo"]
extensiones = [".txt", ".pdf", ".jpg", ".csv", ".docx"]

os.makedirs(raiz, exist_ok=True)

directorios = [raiz]

# Crear subdirectorios aleatorios
for i in range(random.randint(5, 12)):
    padre = random.choice(directorios)
    nuevo = os.path.join(padre, f"subdir_{i}")
    os.makedirs(nuevo, exist_ok=True)
    directorios.append(nuevo)

# Crear aproximadamente 100 archivos
for i in range(100):
    directorio = random.choice(directorios)

    if random.random() < 0.15:
        nombre = "archivo.txt"
    else:
        nombre = random.choice(nombres) + f"_{i}" + random.choice(extensiones)

    ruta = os.path.join(directorio, nombre)

    with open(ruta, "w") as f:
        f.write("Archivo de prueba\n")

print("Directorio creado correctamente.")
print(f"{len(directorios)} creados")