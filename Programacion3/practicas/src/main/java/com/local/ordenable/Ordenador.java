package com.local.ordenable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ordenador {
    public static List<Ordenable> ordenar(Object[] lista) {
        List<Ordenable> salida = new ArrayList<>();
        Arrays.sort(lista);
        for (Object object : lista) {
            Ordenable elem = (Ordenable)object;
            salida.add(elem);
        }
        return salida;
    }
}
