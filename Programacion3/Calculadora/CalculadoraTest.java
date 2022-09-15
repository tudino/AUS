package Calculadora;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CalculadoraTest {

	@Test
	void testSuma() {
		
		Calculadora C = new Calculadora();
		double resultSuma = C.sumar(2, 3, 5);
		assertEquals(10, resultSuma);
	}
	
	
	@Test
	void testResta() {
		Calculadora C = new Calculadora();
		double resultResta = C.restar(2, 3, 5);
		assertEquals(-6, resultResta);
	}
	
	@Test
	void testMultiplicar() {
		Calculadora C = new Calculadora();
		double resultMultiplicar = C.multiplicar(2, 0, 3, 5);
		assertEquals(0, resultMultiplicar);
	}
	
	@Test
	void testCal() {
		Calculadora C = new Calculadora();
		double result;
		//double res;
		result = C.sumar(2,3);
		result = C.restar(result, 1);
		assertEquals(4, result);
		
	}
	
	@Test()
	void testMultiplicaUno() throws Exception {
		Calculadora C = new Calculadora();
		try {
		    C.multiplicar(3);
		    fail("Deberia tirar Exception");
		} catch (MultiplicarException e) {
			
		}
		// assertEquals(0, resultMultiplicar);
	}
	
	@Test
	void testDividir() {
		Calculadora C = new Calculadora();
		double resultDividir = C.dividir(5, 3, 2);
		assertEquals(0.83, resultDividir,0.01);
	
	}
	
}
