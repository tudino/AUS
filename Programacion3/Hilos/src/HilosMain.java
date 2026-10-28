
public class HilosMain {
	public static void main(String [] args) {
		Contador contador = new Contador();
		
		HiloPositivo hp = new HiloPositivo(contador);
		HiloNegativoRunnable hnr = new HiloNegativoRunnable(contador);
		
		Thread hn = new Thread(hnr);
		
		hp.start();
		hn.start();
		
		try {
			hp.join();
			System.out.println("Hilo Positivo \t" + contador.getValue());
			
			hn.join();
			System.out.println("Hilo Negativo \t" + contador.getValue());
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		
		System.out.println("==============================================");
		System.out.println(contador.getValue());
	}

}
