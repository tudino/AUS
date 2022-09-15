public class Juego {

	public static void main(String [] args) {
		
		Mazo miMazo = new Mazo();
		Carta miCarta;
		
		for (int i = 0; i < 48; i++) {
			miCarta = miMazo.sacarCarta();
			miCarta.mostrar();
		}
	}
}
