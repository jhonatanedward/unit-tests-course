package br.edward.servicos;

import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.edward.entidades.Filme;
import br.edward.entidades.Locacao;
import br.edward.entidades.Usuario;
import br.edward.exceptions.FilmeSemEstoqueException;
import br.edward.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoValorAlocacaoTest {
	
	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value=1)
	public Double valorLocacao;
	
	@Parameter(value=2)
	public String nomeTest;
	
	public LocacaoService service;
	
	@Before
	public void setup() {
		service = new LocacaoService();
	}
	
	private static Filme filme1 = new Filme("Filme 1", 2, 4.0);
	private static Filme filme2 = new Filme("Filme 2", 2, 4.0);
	private static Filme filme3 = new Filme("Filme 3", 2, 4.0);
	private static Filme filme4 = new Filme("Filme 4", 2, 4.0);
	private static Filme filme5 = new Filme("Filme 5", 2, 4.0);
	private static Filme filme6 = new Filme("Filme 6", 2, 4.0);
	private static Filme filme7 = new Filme("Filme 6", 2, 4.0);
	
	@Parameters(name = "{2}")
	public static Collection<Object[]> getParameters(){
		return Arrays.asList(new Object[][] {
			{Arrays.asList( filme1, filme2), 8.0, "2 filmes sem desconto"},
			{Arrays.asList( filme1, filme2, filme3 ), 11.0, "3 filmes 25%"},
			{Arrays.asList( filme1, filme2, filme4, filme4), 13.0,"4 filmes 50%"},
			{Arrays.asList( filme1, filme2, filme4, filme4, filme5), 14.0, "5 filmes 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 filmes 100%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "7 filmes sem desconto"},
		});
	}
	
	@Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("usuario 1");
		
		// acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		Assert.assertThat(resultado.getValor(), is(valorLocacao));
	}
	
	@Test
	public void print() {
		System.out.println(valorLocacao);
	}
	
}












