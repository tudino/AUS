package com.local.ordenable;

public class Pelicula implements Ordenable {
    public String titulo;
    public String autor;
    public int anio;

    public Pelicula(String tt, String au, int an) {
        titulo = tt;
        autor = au;
        anio = an;
    }

    @Override 
    public int compareTo(Object otro) {
        if (otro instanceof Pelicula o) {
            return titulo.compareTo(o.titulo);
        }
        return 0;
    }
}
