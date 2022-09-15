package Geometria;

public class Punto3D extends Punto {

    private int z = 0;

    public Punto3D(int x0, int y0, int z0) {
        super(x0, y0);
        z = z0;
    }

    public void setZ(int z0) { z = z0; }
    public int getZ() { return z; }
    
    @Override
    public double distanceToOrigin() {
        return Math.sqrt(getX()^2 + getY()^2 + z^2);
    }

    @Override
    public void print() {
        System.out.println("Punto 3D {" + getX() + ", " + getY() + ", " + z + "}");
    }
}
