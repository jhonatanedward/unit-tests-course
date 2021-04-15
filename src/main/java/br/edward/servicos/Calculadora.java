package br.edward.servicos;

import br.edward.exceptions.NaoPodeDividirPorZeroException;

public class Calculadora {

	public int soma(int a, int b) {
		return a + b;
	}

	public int subtrair(int a, int b) {
		return a - b;
	}

	public int dividir(int i, int j) throws NaoPodeDividirPorZeroException {
		if(j == 0) {
			throw new NaoPodeDividirPorZeroException();
		}
		return i / j;
	}
	
	public int divide(String a, String b) {
		return Integer.valueOf(a) / Integer.valueOf(b);
	}

}
