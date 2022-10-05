package Contenedores;
import java.util.Set;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        Persona p1 = new Persona("Angel Tulio", 66);
        Persona p2 = new Persona("Angel Tulio", 66);
        Persona p3 = new Persona("Angel Tulio", 66);
        Persona p4 = new Persona("Angel Tulio", 66);
        
        Set<Persona> mySet = new HashSet<Persona>(); 

        mySet.add(p1); // Este lo agrega
        mySet.add(p2); // Este lo agrega
        mySet.add(p3); // Este lo agrega
        mySet.add(p4); // Este lo agrega
        mySet.add(p1); // Este no lo agrega porque ya existe
        
        for (Persona elem : mySet) {
        	System.out.println(elem.name);        	
        }
        
    }
}
