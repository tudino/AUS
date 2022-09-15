package Calculadora;

public class Calculadora {
	
	private double tmp = 0;
	
	public double sumar(double ...val) {
		int i;
		
		for(i = 0; i < val.length; i++) {
			tmp = tmp + val[i];
		}

		return tmp;
	}
	
	public double restar(double ...val) {
		int i;
		tmp = val[0] - val[1];

		for(i = 2; i < val.length; i++) {
			tmp = tmp - val[i];
		}

		return tmp;
	}
	
	public double multiplicar(double ...val) {
		int i;
		if (val.length == 1) {
			throw new MultiplicarException();
		}
		tmp = val[0] * val[1];
		
		for(i = 2; i < val.length; i++) {
			if(val[i] == 0) {
				return 0;
			}
			tmp = tmp * val[i];
		}

		return tmp;
	}
	
	public double dividir(double ...val) {
		int i;
		tmp = val[0] / val[1];
		
		for(i = 2; i < val.length; i++) {
			tmp = tmp / val[i];
		}

		return tmp;
	}
	
}
