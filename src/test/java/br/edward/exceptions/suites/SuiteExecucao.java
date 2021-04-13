package br.edward.exceptions.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.edward.servicos.CalculadoraTest;
import br.edward.servicos.CalculoValorAlocacaoTest;
import br.edward.servicos.LocacaoServiceTest;

@RunWith(Suite.class)
@SuiteClasses({
	CalculadoraTest.class,
	CalculoValorAlocacaoTest.class,
	LocacaoServiceTest.class	
})
public class SuiteExecucao {
	@BeforeClass
	public static void before() {
		System.out.println("Before");
	}
	
	@AfterClass
	public static void after() {
		System.out.println("After");
	}
}
