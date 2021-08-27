public class Punto {
    private int x = 0;
    private int y = 0;

    public Punto(int x0, int y0) {
        x = x0;
        y = y0;
    }
    // public Punto() {}

    public void print() {
        System.out.println("Punto: ");
        System.out.println("    x " + x);
        System.out.println("    y " + y);    
    }
}
