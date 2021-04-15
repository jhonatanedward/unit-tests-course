package br.edward.servicos;

import static br.edward.builders.FilmeBuilder.umFilme;
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
import org.mockito.Mockito;

import br.edward.daos.LocacaoDAO;
import br.edward.daos.LocacaoDAOFake;
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
		LocacaoDAO dao = Mockito.mock(LocacaoDAO.class);
		service.setLocacaoDAO(dao);
		SPCService spcService = Mockito.mock(SPCService.class);
		service.setSpcService(spcService);
	}
	
	private static Filme filme1 = umFilme().agora();
	private static Filme filme2 = umFilme().agora();
	private static Filme filme3 = umFilme().agora();
	private static Filme filme4 = umFilme().agora();
	private static Filme filme5 = umFilme().agora();
	private static Filme filme6 = umFilme().agora();
	private static Filme filme7 = umFilme().agora();
	
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
		
		System.out.println("!");
	}
	
	@Test
	public void print() {
		System.out.println(valorLocacao);
	}
	
}












