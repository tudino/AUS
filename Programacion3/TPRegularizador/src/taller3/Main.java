package taller3;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class Main {

	// Limite maximo de hilos a ejecutar
	public static int MAX_HILOS = 10; 
	
	// Recorre recursivamente el directorio de entrada almacenando cada Directorio en una lista.
	public static List<Directorio> recorrerDirectorios(File directorioRaiz) {
		
	    List<Directorio> directorios = new ArrayList<>();
	    directorios.add(new Directorio(directorioRaiz));

	    File[] elementos = directorioRaiz.listFiles();

	    if (elementos == null) return directorios;

	    for (File f : elementos) {
	        if (f.isDirectory()) {
	            directorios.addAll(recorrerDirectorios(f));
	        }
	    }

	    return directorios;
	}
	
	
	public static void main(String[] args) throws InterruptedException {

		// Lectura de datos de entrada
		DatosEntrada entrada = new DatosEntrada();
		File directorioEntrada = entrada.leerDirectorio();
		String nombreArchivoEntrada = entrada.leerNombreArchivo();
		int hilosEntrada = entrada.leerCantidadHilos();
		
		// Obtiene una lista con los directorios (Clase Directorio)
		List<Directorio> directorios = new ArrayList<>();
		directorios = recorrerDirectorios(directorioEntrada);
				
		// Archivos + Carpetas
		int cantidadArchivos = 0; 
		
		// Cuenta la totalidad de archivos y carpetas presentes en el directorio de entrada
		for (Directorio directorio : directorios) {
			if (directorio.getFile().isDirectory()) {
				cantidadArchivos += directorio.getCantidadTotalDeArchivos();				
			}
		}
		
		// Operacion para definir la cantidad de archivos que procesara cada hilo
		// La carga es equitativa, algunos hilos reciben 1 archivo extra
		int cantidadHilos = Math.min(hilosEntrada, MAX_HILOS);
		int base = cantidadArchivos / cantidadHilos;
		int resto = cantidadArchivos % cantidadHilos;
		
		// Solo para control de resultados al finalizar
		List<HiloBuscador> buscadores = new ArrayList<>(); 

		
		List<Thread> hilos = new ArrayList<>();
		
		// Por cada hilo se genera un HiloBuscador y se le asignan Tareas
		for (int i = 0; i < cantidadHilos; i++) {
			// Total de archivos que debe procesar el hilo
		    int cuposDisponibles = base + (i < resto ? 1 : 0);
		    
		    HiloBuscador buscador = new HiloBuscador(nombreArchivoEntrada);
		    buscadores.add(buscador);
		    
		    for (Directorio directorio : directorios) {
		    	// Archivos del directorio sin asignar
		        int archivosSinAsignar = directorio.getCantidadTotalDeArchivos() - directorio.getCursor();

		        if (archivosSinAsignar <= 0 || cuposDisponibles <= 0) continue;
		        
		        // Se asigna la cantidad que mejor se ajusta, se crea una Tarea y se la agrega al hilo buscador
		        int asignados = Math.min(cuposDisponibles, archivosSinAsignar);
		        Tarea tarea = new Tarea();
		        tarea.directorio = directorio;
		        tarea.desde = directorio.getCursor();
		        tarea.hasta = tarea.desde + asignados;
		        buscador.agregarTarea(tarea);	        
		       
		        // Se actualiza el cursor del directorio, indicando hasta que posicion se han asignado archivos
		        directorio.setCursor(tarea.hasta);
		        cuposDisponibles -= asignados;
		    }

		    Thread hilo = new Thread(buscador, "Hilo-" + i);
		    hilos.add(hilo);
		}
		
		for (Thread hilo : hilos) {
		    hilo.start();
		}
		
		for (Thread hilo : hilos) {
		    hilo.join();
		}
		
		// Cantidad de archivos encontrados en todos los hilos
		int ocurrencias = 0; 
		
		for (HiloBuscador buscador : buscadores) {
			ocurrencias += buscador.getOcurrencias();
		}
		
		System.out.println("==========================================");
		System.out.println("Total directorios = " + directorios.size());
		System.out.println("Total ocurrencias = " + ocurrencias);
		System.out.println("==========================================");
	}
}
