Trabajo Practico Final - Contenedores Docker
Complejidad: Baja

Descripcion
Realizar una aplicacion web con FastApi que sirva un frontend en vanilla js. 
Persistir los datos en una base de datos en mongo db y desplegar en docker utilizando docker compose.
La aplicacion es una biblioteca de libros, el objetivo es almacenar un listado de libros y hacer alta baja modificacion desde la web.
La web debe ser sencilla. Dividir la pantalla en 2 por la mitad.
1. En panel izquierdo mostrar:
   1. una caja de busqueda de texto para buscar por titulo o editorial. Debe haber un boton X para limpiar el searchbox.
   2. el listado de todos los libros filtrados o sin filtrar. A la derecha de cada elemento hay un icono de una papelera para eliminarlo. 
   3. Al clickear en la papelera aparece un toast para Confirmar o Cancelar la operacion de borrar. Esta operacion elimina el elemento de la base de datos. Luego de borrarse correctamente aparece un mensaje toast con el mensaje "Libro eliminado" en verde. Si hay un error muestra el mensaje en rojo.
2. En el panel derecho un formulario estatico con los campos de la base de datos. Debajo hay 3 botones Limpiar, Agregar.
   1. para agregar un nuevo libro, se debe completar el formulario y presionar Agregar. El formulario debe validar los datos de entrada, y mostrar un mensaje en rojo si algo no cumple los requisitos. Al darle click en Agregar se agrega la entrada a la base de datos. Luego de insertarse correctamente aparece un mensaje toast con el mensaje "Libro agregado" en verde. Si hay un error muestra el mensaje en rojo. Finalmente se limpia automaticamente el formulario
   2. al clickear Limpiar aparece un toast para Confirmar o Cancelar la operacion de limpiar
   3. al seleccionar un elemento de la lista del panel izquierdo, esta se carga en el formulario de panel derecho para poder modificar los campos. Y aparece un nuevo boton escondido Modificar para que se alteren los datos en la base de datos. Luego de modificarse correctamente aparece un mensaje toast con el mensaje "Libro modificado" en verde. Si hay un error muestra el mensaje en rojo. Finalmente se limpia automaticamente el formulario


Entidad Libro en la base de datos
- id (campo único) -> es un campo que se ingresa de forma manual. Antes de modificar o agregar una nueva entrada debe validarse
- titulo del libro
- cantidad paginas
- editorial
- isbn
- costo


Stack tecnologico
- Backend: FastAPI
- Frontend: Vanilla js (javascript puro) + TailwindCSS
- DB: MongoDB
- Contenedores: Docker y Docker Compose


Antes de comenzar se debe crear
- Carpeta `backend` para el codigo de FastAPI
- Archivo `backend/requirements.txt` para las dependencias del backend
- Carpeta `frontend` para el codigo de la interfaz de usuario
- Archivo `.env` para las variables de entorno
- Archivo `Dockerfile` para la imagen de docker de la aplicacion
- Archivo `docker-compose.yml` para el despliegue de los servicios involucrados


Dockerfile
- Debe extender de la imagen de python 3.13
- Debe instalar las dependencias registradas en requirements.txt
- Debe agregar la carpeta frontend para servir los archivos estaticos
- Debe iniciar con el entrypoint sirviendo la aplicacion FastAPI run


docker-compose.yml
- Aplicacion Web: debe levantar la imagen construida y servir la aplicacion web completa en el puerto 8000
- Base de datos: debe levantar una imagen de MongoDB en el puerto estandar
- Network: debe existir una red explicita bajo el nombre `app-network`
- Volume: la base de datos debe persistirse en un volumen de docker llamado `mongodb-volume`
