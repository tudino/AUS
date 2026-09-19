package com.local;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.local.aabb.Punto;
import com.local.aabb.Punto3D;
import com.local.aabb.Prisma;

/**
 * Unit test for PuntoTest.
 */
public class AABBTest 
{
    /**
     * Test Punto
     */
    @Test
    public void puntoTest()
    {
        Punto p = new Punto(0, 0);

        assertTrue(p.getX() == 0);
        assertTrue(p.getY() == 0);

        p.setX(10);
        p.setY(20);
        
        assertTrue(p.getX() == 10);
        assertTrue(p.getY() == 20);
    }

    /**
     * Test Punto3D
     */
    @Test
    public void punto3DTest()
    {
        Punto3D p = new Punto3D(0, 0, 0);

        assertTrue(p.getX() == 0);
        assertTrue(p.getY() == 0);
        assertTrue(p.getZ() == 0);

        p.setX(10);
        p.setY(20);
        p.setZ(30);
        
        assertTrue(p.getX() == 10);
        assertTrue(p.getY() == 20);
        assertTrue(p.getZ() == 30);
    }

    /**
     * Test Colision Box3D
     */
    @Test
    public void colisionTest()
    {
        Prisma p1 = new Prisma(new Punto3D(0, 0, 0), new Punto3D(1, 1, 1));
        Prisma p2 = new Prisma(new Punto3D(2, 2, 2), new Punto3D(3, 3, 3));
        
        assertFalse(p1.colisiona(p2));
        assertFalse(p2.colisiona(p1));

        Prisma p3 = new Prisma(new Punto3D(0, 0, 0), new Punto3D(3, 3, 3));
        Prisma p4 = new Prisma(new Punto3D(1, 2, 2), new Punto3D(3, 3, 3));

        assertTrue(p3.colisiona(p4));
        assertTrue(p4.colisiona(p3));
    }
}
