package taller3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HiloBuscador implements Runnable {
	
    private List<Tarea> tareas;
    private String nombreArchivo;
    private int ocurrencias = 0;
    private static final Object mutex = new Object();

    public HiloBuscador(String nombre) {
    	this.tareas = new ArrayList<>();
    	this.nombreArchivo = nombre;
    }
    
    public void agregarTarea(Tarea t) {
        this.tareas.add(t);
    }
    
    public int getOcurrencias() {
    	return this.ocurrencias;
    }
	
    @Override
    public void run() {
        for (Tarea tarea : tareas) {
            File[] elementos = tarea.directorio.getFile().listFiles();

            for (int i = tarea.desde; i < tarea.hasta; i++) {
            	// Mutex para evitar concurrencia al imprimir por pantalla y sumar ocurrencias
            	synchronized (mutex) {
            		File archivo = elementos[i];
            		
            		if (archivo.isFile() && archivo.getName().equals(this.nombreArchivo)) {
            			this.ocurrencias++;
            			System.out.println("------------------------------");
            			System.out.println(Thread.currentThread().getName());
            			System.out.println(archivo.getAbsolutePath());
            		}            		
            	}
            }
        }
    }
}