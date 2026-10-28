
public class Productor extends Thread {
	private Pila pila;
	
	public Productor(Pila p) {
		pila = p;
	}
	
	@Override
	public void run() {
		for (int i=0; i<200; i++) {
			char tmp = (char) ('A' + (Math.random()*26));
			pila.push(tmp);
			System.out.println(Thread.currentThread().getName() + " " + tmp);
			
			try {
				Thread.sleep(100 + (int) Math.random() * 300);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}
}
