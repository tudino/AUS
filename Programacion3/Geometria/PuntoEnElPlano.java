package Geometria;

public class PuntoEnElPlano {
    
    private int[] punto = {0, 0};
    
    public PuntoEnElPlano() {
        this(0, 0);
    }
    
    public PuntoEnElPlano(int x, int y) {
        this.punto[0] = x;
        this.punto[1] = y;
    }
    
    public PuntoEnElPlano(String c) {
        double rx = Math.random()*100;
        double ry = Math.random()*100;
        this.punto[0] = (int)rx;
        this.punto[1] = (int)ry;
    }
    
    public String getPos() {
        return "x: " + this.punto[0] + " y: " + this.punto[1];
    }
    
    public String getCuad() {
        if(this.punto[0] >= 0 && this.punto[1] >= 0) { return "Cuadrante 1"; }
        if(this.punto[0] < 0 && this.punto[1] > 0) { return "Cuadrante 2"; }
        if(this.punto[0] < 0 && this.punto[1] < 0) { return "Cuadrante 3"; }
        
        return "Cuadrante 4";
    }
    
    public double distTo(int dx, int dy) {
        int xx = 0;
        int yy = 0;
        
        if(this.punto[0] > dx) {
            xx = this.punto[0] - dx;
        } else {
            xx = dx - this.punto[0];
        }
        
        if(this.punto[1] > dy) {
            yy = this.punto[1] - dy;
        } else {
            yy = dy - this.punto[1];
        }
        
        
        return Math.sqrt(xx^2 + yy^2);
    }
    
    public static void main(String args[]) {
        
        PuntoEnElPlano pp1 = new PuntoEnElPlano();
        PuntoEnElPlano pp2 = new PuntoEnElPlano(10, 10);
        PuntoEnElPlano pp3 = new PuntoEnElPlano("r");
        
        System.out.println("---- PuntoEnElPlano 1 ----");
        System.out.println(pp1.getPos());
        System.out.println(pp1.getCuad());
        System.out.println(pp1.distTo(40, 55));
        
        System.out.println("---- PuntoEnElPlano 2 ----");
        System.out.println(pp2.getPos());
        System.out.println(pp2.getCuad());
        System.out.println(pp2.distTo(60, -5));
        
        System.out.println("---- PuntoEnElPlano 3 ----");
        System.out.println(pp1.getPos());
        System.out.println(pp1.getCuad());
        System.out.println(pp1.distTo(-10, 35));
    }
}
