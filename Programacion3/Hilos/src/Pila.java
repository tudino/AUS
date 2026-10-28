
public class Pila {
	private char [] elementos;
	private int idx = 0;
	
	public Pila() {
		elementos = new char [4];
	}
	
	public synchronized void push(char c) {
		while (idx == elementos.length) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
		elementos[idx++] = c;
		notify();
	}
	
	public synchronized char pop() {
		while (idx == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
		char tmp = elementos[--idx];
		notify();
		return tmp;
	}
	
	public int size() {
		return idx;
	}
}
