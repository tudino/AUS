package taller3;

import java.io.File;
import java.util.Scanner;


public class DatosEntrada {

    private Scanner scanner = new Scanner(System.in);

    public File leerDirectorio() {
        File directorio;

        do {
            System.out.println("Ingrese la ruta del directorio:");
            directorio = new File(scanner.nextLine());

        } while (!directorio.isDirectory());

        return directorio;
    }

    public String leerNombreArchivo() {
        String nombre;

        do {
            System.out.println("Ingrese el nombre del archivo:");
            nombre = scanner.nextLine();

        } while (nombre.isBlank()
                || nombre.contains("/")
                || nombre.contains("\\")
                || nombre.equals(".")
                || nombre.equals(".."));

        return nombre;
    }

    public int leerCantidadHilos() {
        int cantidad;

        while (true) {
            System.out.println("Ingrese la cantidad de hilos:");

            if (scanner.hasNextInt()) {
                cantidad = scanner.nextInt();
                scanner.nextLine();

                if (cantidad > 0) {
                    return cantidad;
                }
            } else {
                scanner.nextLine();
            }
        }
    }
}