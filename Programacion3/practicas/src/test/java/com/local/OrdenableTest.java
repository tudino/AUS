package com.local;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.local.ordenable.Libro;
import com.local.ordenable.Ordenable;
import com.local.ordenable.Ordenador;
import com.local.ordenable.Pelicula;

/**
 * Unit test for simple App.
 */
public class OrdenableTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void ordenaTrue()
    {
        List<Ordenable> libros = new ArrayList<>();
        List<Ordenable> peliculas = new ArrayList<>();

        libros.add(new Libro("CC", "Lucas", 1989));
        libros.add(new Libro("AA", "Lucas", 1992));
        libros.add(new Libro("BB", "Lucas", 1985));
        
        peliculas.add(new Pelicula("SS", "Lucas", 1982));
        peliculas.add(new Pelicula("VV", "Lucas", 1985));
        peliculas.add(new Pelicula("EE", "Lucas", 1989));
        
        List<Ordenable> ordenado = new ArrayList<Ordenable>();
        
        System.out.println("Libros");
        ordenado = Ordenador.ordenar(libros.toArray());

        for (Ordenable libro : ordenado) {
            if (libro instanceof Libro l) {
                System.out.println(l.titulo);   
            }
        }

        System.out.println("Peliculas");
        ordenado = Ordenador.ordenar(peliculas.toArray());

        for (Ordenable peli : ordenado) {
            if (peli instanceof Pelicula p) {
                System.out.println(p.titulo);   
            }
        }

        // assertTrue( true );
    }
}
