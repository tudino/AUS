package com.local.aabb;

public class Punto3D extends Punto {
    private int z;

    public Punto3D(int xx, int yy, int zz) {
        super(xx, yy);
        z = zz;
    }

    public void setZ(int zz) {
        z = zz;
    }

    public int getZ() {
        return z;
    }
}
