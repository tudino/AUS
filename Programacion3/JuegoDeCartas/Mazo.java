package JuegoDeCartas;

public class Mazo {
	
	private int cant;
	
	private Carta[] cartas;
	
	public Mazo() {
		cant = 48;
		cartas = new Carta[cant];
		Palo[] palos = Palo.values();
		int indice = 0;
		
		for (Palo p : palos) {
			for	(int i = 1; i <= (cant / palos.length); i++) {
				cartas[indice++] = new Carta(p, i);
			}
		}
	}
	
	public Carta sacarCarta() {
		int pos = (int)(Math.random() * cant--);
		Carta tmp = cartas[pos];
		cartas[pos] = cartas[cant];
		cartas[cant] = tmp;
		
		return cartas[cant];
	}
}
