package br.edward.servicos;

import static br.edward.builders.FilmeBuilder.umFilme;
import static br.edward.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.edward.builders.LocacaoBuilder.umLocacao;
import static br.edward.builders.UsuarioBuilder.umUsuario;
import static br.edward.exceptions.matchers.MatchersProprios.caiEm;
import static br.edward.exceptions.matchers.MatchersProprios.caiNumaSegunda;
import static br.edward.exceptions.matchers.MatchersProprios.ehHoje;
import static br.edward.exceptions.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.edward.builders.LocacaoBuilder;
import br.edward.daos.LocacaoDAO;
import br.edward.entidades.Filme;
import br.edward.entidades.Locacao;
import br.edward.entidades.Usuario;
import br.edward.exceptions.FilmeSemEstoqueException;
import br.edward.exceptions.LocadoraException;
import br.edward.utils.DataUtils;

public class LocacaoServiceTest {
	
	@InjectMocks
	private LocacaoService service;
	
	@Mock
	private SPCService spcService;
	
	@Mock
	private EmailService emailService;
	
	@Mock
	private LocacaoDAO dao;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void deveAlugarFilme() throws Exception{
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cenario
		
		Usuario usuario = umUsuario().agora();
		
		List<Filme> filmes = Arrays.asList(umFilme().comValor(5.0).agora());
		
		//acao
		Locacao locacao;
		
		locacao = service.alugarFilme(usuario, filmes);
		//verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
//		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(locacao.getDataLocacao(), ehHoje());
//		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
	}
	
	@Test(expected=FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception{
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilmeSemEstoque().agora());
		
		//acao
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void deveLancarExcecaoAoAlugarFilmeSemEstoqueFormaDois() {
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilmeSemEstoque().agora());
		
		//acao
		try {
			service.alugarFilme(usuario, filmes);
			Assert.fail("Deveria ter lancado uma excecao...");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}
	
	@Test
	public void testeLocacao_filmeSemEstoque_3() throws Exception{
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilmeSemEstoque().agora());
		
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
		
		//acao
		service.alugarFilme(usuario, filmes);	
	}
	
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		
		// cenario
		LocacaoService service = new LocacaoService();
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		// acao
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuario vazio"));
		}
		
		// Maior controle sobre o fluxo, posso exibir mensagem...
	}
	
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = umUsuario().agora();
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		// acao
		service.alugarFilme(usuario, null);
		
	}
	
	@Test
	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(
				umFilme().agora(),
				umFilme().agora(), 
				umFilme().agora());
		
		// acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
			Assert.assertThat(resultado.getValor(), is(11.0));
	}
	
	@Test
	public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("usuario 1");
		List<Filme> filmes = Arrays.asList(
				umFilme().agora(),
				umFilme().agora(), 
				umFilme().agora(),
				umFilme().agora());
		
		// acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		Assert.assertThat(resultado.getValor(), is(13.0));
	}
	
	@Test
	public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0),
				new Filme("Filme 2", 2, 4.0), 
				new Filme("Filme 3", 2, 4.0),
				new Filme("Filme 4", 2, 4.0),
				new Filme("Filme 5", 2, 4.0)
				);
		
		// acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		Assert.assertThat(resultado.getValor(), is(14.0));
	}
	
	@Test
	public void devePagar0PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(
				umFilme().agora(),
				umFilme().agora(), 
				umFilme().agora(),
				umFilme().agora(),
				umFilme().agora(),
				umFilme().agora()
				);
		
		// acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		Assert.assertThat(resultado.getValor(), is(14.0));
	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		// So executa se for sabado.
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cenario
		Usuario usuario = umUsuario().agora();
		
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		// acao
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
//		assertThat(retorno.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
		
		assertThat(retorno.getDataRetorno(), caiEm(Calendar.MONDAY));
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
	}
	
	@Test
	public void naoDeveAlugarFilmeParaNegativadoSpfc() throws Exception{
		
		// cenario
		Usuario usuario = umUsuario().agora();
		Usuario usuario2 = umUsuario().comNome("usuario 2").agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		when(spcService.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);
		
		
		// acao
		try {
			service.alugarFilme(usuario, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuario negativado"));
		}
		
		// verificacao
		verify(spcService).possuiNegativacao(usuario);
		
	}
	
	@Test
	public void deveEnviarEmailParaLocacoesAtrasadas() {
		// cenario
		
		Usuario usuario = umUsuario().agora();
		Usuario usuario2 = umUsuario().comNome("Usuario em dia").agora();
		Usuario usuario3 = umUsuario().comNome("Outro atrasado").agora();
		
		List<Locacao> locacoes = 
				Arrays.asList(
						umLocacao().atrasado().comUsuario(usuario).agora(),
						umLocacao().comUsuario(usuario2).agora(),
						umLocacao().atrasado().comUsuario(usuario3).agora(),
						umLocacao().atrasado().comUsuario(usuario3).agora()
						);
		
		when(dao.obterLocacoesPendentes()).thenReturn(locacoes);
		
		// acao
		service.notificarAtrasos();
		
		// verificacao
		verify(emailService, times(3)).notificarAtraso(Mockito.any(Usuario.class));
		verify(emailService).notificarAtraso(usuario);
		verify(emailService, Mockito.atLeastOnce()).notificarAtraso(usuario3);
		verify(emailService, never()).notificarAtraso(usuario2);
		verifyNoMoreInteractions(emailService);
	}
	

	@Test
	public void deveTratarErronoSPC() throws Exception {
		// cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		when(spcService.possuiNegativacao(usuario)).thenThrow(new Exception("Problemas com SPC, tente novamente"));
		
		// verificacao
		exception.expect(LocadoraException.class);
		exception.expectMessage("Problemas com SPC, tente novamente");
		
		// acao
		service.alugarFilme(usuario, filmes);		
	}
	
	@Test
	public void deveProrrogarUmaLocacao() {
		// cenario
		Locacao locacao = LocacaoBuilder.umLocacao().agora();
		
		// acao
		service.prorrogarLocacao(locacao, 3);
		
		// verificacao
		ArgumentCaptor<Locacao> argCapt = ArgumentCaptor.forClass(Locacao.class);
		
		Mockito.verify(dao).salvar(argCapt.capture());
		
		Locacao locacaoRetornada = argCapt.getValue();
		
		error.checkThat(locacaoRetornada.getValor(), is(12.0));
		error.checkThat(locacaoRetornada.getDataLocacao(), ehHoje());
		error.checkThat(locacaoRetornada.getDataRetorno(), ehHojeComDiferencaDias(3));
	}
}

















