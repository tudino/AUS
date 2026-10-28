
public class Contador {
	public int value;
	
	public synchronized void inc() {
		value++;
	}
	
	public synchronized void dec() {
		value--;
	}
	
	public synchronized int getValue() {
		return value;
	}
}
