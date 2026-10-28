

public class HiloPositivo extends Thread {
	
	public Contador contador;
	
	public HiloPositivo(Contador cont) {
		contador = cont;
	}
	
	@Override
	public void run () {
		for (int i=0; i<10000; i++) {
			contador.inc();
			//System.out.println(i);
		}
	}
}
