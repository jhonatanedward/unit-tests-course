package br.edward.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.edward.exceptions.NaoPodeDividirPorZeroException;


public class CalculadoraTest {
	
	Calculadora calculadora;
	
	@Before
	public void beforeEachTest() {
		calculadora = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisNumeros() {
		
		// Cenario
		
		int a = 10;
		int b = 20;
		
		// acao
		int resultado = calculadora.soma(a,b);
		
		// verificacao
		Assert.assertEquals(30, resultado);
	}
	
	@Test
	public void deveSubtrairDoisNumeros() {
		
		// cenario
		
		int a = 10;
		int b = 5;
		
		// acao
		int resultado = calculadora.subtrair(a,b);
		
		// verificacao
		Assert.assertEquals(5, resultado);
		
	}
	
	@Test
	public void deveDividir() throws NaoPodeDividirPorZeroException {
		
		// cenario
		int i = 10;
		int j = 2;
		
		// acao
		int resultado = calculadora.dividir(i, j);		
		
		// verificacao
		Assert.assertEquals(5, resultado);
		
	}
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {

		// cenario
		int i = 10;
		int j = 0;
		
		// acao
		int resultado = calculadora.dividir(i, j);
	}	
}