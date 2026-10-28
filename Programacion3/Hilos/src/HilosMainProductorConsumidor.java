
public class HilosMainProductorConsumidor {
	public static void main(String [] args) {
		Pila pila = new Pila();
		
		Consumidor cons = new Consumidor(pila);
		Productor prod = new Productor(pila);
		
		prod.start();
		cons.start();
		
		
		try {
			prod.join();		
			cons.join();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		
		System.out.println("==============================================");		
	}

}
