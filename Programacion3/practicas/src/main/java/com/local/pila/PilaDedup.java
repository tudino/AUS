package com.local.pila;

public class PilaDedup extends Pila {

    public PilaDedup() {
        super();
    }

    @Override 
    public void push(Object obj) {
        // Coloca el objeto duplicado en lo alto de la pila y elimina los duplicados en posiciones anteriores
        int index = 0;
        for (Object object : pila) {
            if (object.equals(obj)) {
                pila.remove(index);
                break;
            }
            index++;
        }
        super.push(obj);
    }
}
