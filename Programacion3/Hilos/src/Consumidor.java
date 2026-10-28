
public class Consumidor extends Thread {
	private Pila pila;
	
	public Consumidor(Pila p) {
		pila = p;
	}
	
	@Override
	public void run() {
		for (int i=0; i<200; i++) {
			char c = pila.pop();
			
			System.out.println(Thread.currentThread().getName() + " " + c);
			
			try {
				Thread.sleep(100 + (int) Math.random() * 300);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}
}
