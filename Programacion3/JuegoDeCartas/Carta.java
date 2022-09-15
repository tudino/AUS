public class Carta {
	
	public final Palo palo;
	public final int numero;
	
	public Carta (Palo p, int n) {
		palo = p;
		numero = n;
	}
	
	public void mostrar() {
		System.out.println(numero + " de " + palo);
	}
}
