package com.local.pila;

import java.util.ArrayList;
import java.util.List;

public class Pila {
    protected List<Object> pila = new ArrayList<Object>();

    public Pila() {}

    public void push(Object obj) {
        pila.add(obj);
    }
    
    public Object pop() {
        if (this.size() == 0) {
            return null;
        }
        Object last;
        last = pila.removeLast();
        return last;
    }

    public int size() {
        return pila.size();
    }
}
