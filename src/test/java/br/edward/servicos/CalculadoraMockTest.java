package br.edward.servicos;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class CalculadoraMockTest {
	
	@Mock
	private Calculadora calcMock;
	
	@Spy
	private Calculadora calcSpy;
	
	@Mock
	private EmailService email;
	
	@Before
	public void setup() { 
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void devoMostrarDiferencaEntreMockESpy() {
		
//		Mockito.when(calcMock.soma(1,2)).thenCallRealMethod();
		
		Mockito.when(calcMock.soma(1,2)).thenReturn(5);

		/*
		 * nesta implementação o spy será executado o metodo de soma aqui e lá em baixo
		 * comportamento especifico do spy, ordem de precedencia assim como na matemática
		 * que o java faz.
		 */
//		Mockito.when(calcSpy.soma(1,2)).thenReturn(5);
		
		Mockito.doReturn(5).when(calcSpy).soma(1, 2);
		
		Mockito.doNothing().when(calcSpy).imprime();
		
		System.out.println("Mock: " + calcMock.soma(1, 2));
		
		System.out.println("Spy: " + calcSpy.soma(1, 2));
		
		System.out.println("Mock");
		calcMock.imprime();
		
		System.out.println("Spy");
		calcSpy.imprime();
	}
	
	@Test
	public void teste() {
		
		Calculadora calc = Mockito.mock(Calculadora.class);
		
		ArgumentCaptor<Integer> argCapt = ArgumentCaptor.forClass(Integer.class);
		
		Mockito.when(calc.soma(argCapt.capture(), argCapt.capture())).thenReturn(5);
		
//		System.out.println(calc.soma(1,100000));
//		
//		System.out.println(argCapt.getAllValues());
	}
}
