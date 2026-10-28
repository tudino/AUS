
public class HiloNegativoRunnable implements Runnable {
	
	public Contador contador;
	
	public HiloNegativoRunnable(Contador cont) {
		contador = cont;
	}
	
	public void run () {
		for (int i=0; i>-10000; i--) {
			contador.dec();
			//System.out.println("\t"+i);
		}
	}
}
