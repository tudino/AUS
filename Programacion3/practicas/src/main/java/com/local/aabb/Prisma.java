package com.local.aabb;

public class Prisma {
    private Punto3D p1 = new Punto3D(0, 0, 0);
    private Punto3D p2 = new Punto3D(0, 0, 0);

    public Prisma(Punto3D pp1, Punto3D pp2) {
        int minX = Math.min(pp1.getX(), pp2.getX());
        int maxX = Math.max(pp1.getX(), pp2.getX());
        int minY = Math.min(pp1.getY(), pp2.getY());
        int maxY = Math.max(pp1.getY(), pp2.getY());
        int minZ = Math.min(pp1.getZ(), pp2.getZ());
        int maxZ = Math.max(pp1.getZ(), pp2.getZ());
        
        // p1 es el punto mas cerca al origen
        // p2 es el punto mas lejos del origen
        p1 = new Punto3D(minX, minY, minZ);
        p2 = new Punto3D(maxX, maxY, maxZ);
    }

    public Punto3D getP1() {
        return p1;
    }

    public Punto3D getP2() {
        return p2;
    }

    public boolean colisiona(Prisma o) {
        int xP1 = o.getP1().getX();
        int yP1 = o.getP1().getY();
        int zP1 = o.getP1().getZ();
        int xP2 = o.getP2().getX();
        int yP2 = o.getP2().getY();
        int zP2 = o.getP2().getZ();

        // Detecto cuando no hay colision

        if (p2.getX() < xP1 || xP2 < p1.getX()) {
            return false;
        }

        if (p2.getY() < yP1 || yP2 < p1.getY()) {
            return false;
        }

        if (p2.getZ() < zP1 || zP2 < p1.getZ()) {
            return false;
        }

        return true;
    }
}
