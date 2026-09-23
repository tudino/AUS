package com.local.ordenable;

public class Libro implements Ordenable {
    public String titulo;
    public String autor;
    public int anio;

    public Libro(String tt, String au, int an) {
        titulo = tt;
        autor = au;
        anio = an;
    }

    @Override 
    public int compareTo(Object otro) {
        if (otro instanceof Libro o) {
            return titulo.compareTo(o.titulo);
        }
        return 0;
    }
}
