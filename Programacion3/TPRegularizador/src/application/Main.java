package application;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class Main {

	public static int MAX_HILOS = 10; // Cantidad de archivos que un hilo puede buscar por lote
	
	public static List<Directorio> recorrerDirectorios(File directorioRaiz) {
		System.out.println("Raíz: " + directorioRaiz.getAbsolutePath());
	    List<Directorio> directorios = new ArrayList<>();
	    directorios.add(new Directorio(directorioRaiz));

	    File[] elementos = directorioRaiz.listFiles();

	    if (elementos == null) return directorios;

	    for (File f : elementos) {
	        if (f.isDirectory()) {
	        	System.out.println("Encontrada: " + f.getAbsolutePath());
	            directorios.addAll(recorrerDirectorios(f));
	        }
	    }

	    return directorios;
	}
	
	
	public static void main(String[] args) throws InterruptedException {

		File directorioEntrada = new File("C:\\Users\\PC\\Documents\\Proyectos\\AUS\\Programacion3\\TPRegularizador\\testdir");
		int cantidadHilos = Math.min(10, MAX_HILOS);;
		String nombreArchivo = "archivo.txt";
		
		// Obtiene una lista con los directorios (Clase Directorio)
		List<Directorio> directorios = new ArrayList<>();
        
		directorios = recorrerDirectorios(directorioEntrada);
		System.out.println("Total directorios = " + directorios.size());
		int cantidadArchivos = 0; // Archivos y Carpetas
		
		for (Directorio directorio : directorios) {
			if (directorio.getFile().isDirectory()) {
				cantidadArchivos += directorio.getFile().listFiles().length;				
			}
		}
		
		int base = cantidadArchivos / cantidadHilos;
		int resto = cantidadArchivos % cantidadHilos;
		
		List<Thread> hilos = new ArrayList<>();

		for (int i = 0; i < cantidadHilos; i++) {
		    int pendientes = base + (i < resto ? 1 : 0);
		    HiloBuscador buscador = new HiloBuscador(nombreArchivo, pendientes);
		    
		    for (Directorio directorio : directorios) {
		        int restantes = directorio.getFile().listFiles().length - directorio.getCursor();

		        if (restantes <= 0 || pendientes <= 0) continue;

		        int asignados = Math.min(pendientes, restantes);
		        Tarea tarea = new Tarea();
		        tarea.directorio = directorio;
		        tarea.desde = directorio.getCursor();
		        tarea.hasta = tarea.desde + asignados;

		        buscador.agregarTarea(tarea);
		        directorio.setCursor(tarea.hasta);
		        pendientes -= asignados;
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
		
	}

}
