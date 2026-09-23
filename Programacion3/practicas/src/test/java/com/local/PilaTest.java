package com.local;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.local.pila.Pila;
import com.local.pila.PilaDedup;

/**
 * Unit test for simple App.
 */
public class PilaTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void pilaTest()
    {
        Pila pila = new Pila();
        pila.push("A");
        pila.push("B");
        pila.push("C");
        pila.push(1);
        pila.push(2);
        pila.push(3);
        pila.push(3);

        System.out.println(pila.pop());
        System.out.println(pila.pop());
        System.out.println(pila.pop());
        System.out.println(pila.pop());
        System.out.println(pila.pop());
        System.out.println(pila.pop());
        System.out.println(pila.size());
        // assertTrue( true );
    }

    @Test
    public void pilaDedupTest()
    {
        PilaDedup pila = new PilaDedup();
        pila.push("A");
        pila.push("B");
        pila.push(3);
        pila.push("C");
        pila.push(3);
        pila.push(1);
        pila.push(3);
        pila.push(2);

        System.out.println(pila.size()); //6

        System.out.println(pila.pop());
        System.out.println(pila.pop());
        System.out.println(pila.pop());
        System.out.println(pila.pop());
        System.out.println(pila.pop());
        System.out.println(pila.pop());
        // assertTrue( true );
    }
}
