package com.local.aabb;

public class Punto {
    protected int x;
    protected int y;

    public Punto(int xx, int yy) {
        x = xx;
        y = yy;
    }

    public void setX(int xx) {
        x = xx;
    }

    public void setY(int yy) {
        y = yy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
