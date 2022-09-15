package Geometria;

public class Main {
    
    public static void main(String[] args) {
        Punto p1 = new Punto(10,10);

        Punto p3 = new Punto3D(10, 10, 10);

        p1.print();
        System.out.println(p1.distanceToOrigin());
        p3.print();
        System.out.println(p3.distanceToOrigin());
        
    }
}
