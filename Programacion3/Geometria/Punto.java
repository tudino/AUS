package Geometria;

public class Punto {
    private int x = 0;
    private int y = 0;
    private int z = 0;

    public Punto(int x0, int y0) {
        x = x0;
        y = y0;
    }

    public void setX(int x0) { x = x0; }
    public void setY(int y0) { y = y0; }
    public int getX() { return x; }
    public int getY() { return y; }

    public double distanceToOrigin() {
        return Math.sqrt(x^2 + y^2);
    }


    public void print() {
        System.out.println("Punto {" + x + ", " + y + "}");
    }
}
