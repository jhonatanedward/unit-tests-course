package br.edward.servicos;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class CalculadoraMockTest {
	
	@Test
	public void teste() {
		
		Calculadora calc = Mockito.mock(Calculadora.class);
		
		ArgumentCaptor<Integer> argCapt = ArgumentCaptor.forClass(Integer.class);
		
		Mockito.when(calc.soma(argCapt.capture(), argCapt.capture())).thenReturn(5);
		
		System.out.println(calc.soma(1,100000));
		
		System.out.println(argCapt.getAllValues());
	}
}
