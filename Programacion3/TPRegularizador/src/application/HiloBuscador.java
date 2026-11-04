package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HiloBuscador implements Runnable {
	
	private int cantidadAsignada;
    private List<Tarea> tareas;
    private String nombreArchivo;

    public HiloBuscador(String nombre, int cantidad) {
    	this.tareas = new ArrayList<>();
    	this.nombreArchivo = nombre;
    	this.cantidadAsignada = cantidad;
    }
    
    public void agregarTarea(Tarea t) {
        this.tareas.add(t);
    }
    
	public int getCantidadAsignada() {
		return cantidadAsignada;
	}

	public void setCantidadAsignada(int cantidadAsignada) {
		this.cantidadAsignada = cantidadAsignada;
	}
	
    @Override
    public void run() {
        for (Tarea tarea : tareas) {
            File[] elementos = tarea.directorio.getFile().listFiles();

            for (int i = tarea.desde; i < tarea.hasta; i++) {
                File archivo = elementos[i];

                if (archivo.isFile() && archivo.getName().equals(this.nombreArchivo)) {
                    System.out.println(Thread.currentThread().getName());
                	System.out.println(archivo.getAbsolutePath());
                }
            }
        }
    }
}