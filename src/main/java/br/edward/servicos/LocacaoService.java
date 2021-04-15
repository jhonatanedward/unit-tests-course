package br.edward.servicos;

import static br.edward.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.edward.daos.LocacaoDAO;
import br.edward.entidades.Filme;
import br.edward.entidades.Locacao;
import br.edward.entidades.Usuario;
import br.edward.exceptions.FilmeSemEstoqueException;
import br.edward.exceptions.LocadoraException;
import br.edward.utils.DataUtils;

public class LocacaoService {
	
	private LocacaoDAO dao;
	
	private SPCService spcService;
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException{
		
		if(usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}
		
		if(filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme vazio");
		}
		
		for(Filme filme : filmes) {
			if(filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException("Filme sem estoque");
			}
		}
		
		if(spcService.possuiNegativacao(usuario)) {
			throw new LocadoraException("Usuario negativado");
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorTotal = 0d;
		for(int i = 0; i < filmes.size(); i++) {
			Filme filme = filmes.get(0);
			Double valorFilme = filme.getPrecoLocacao();
			switch (i) {
				case 2:	valorFilme *= 0.75; break;
				case 3: valorFilme *= 0.5; break;
				case 4: valorFilme *= 0.25; break;
				case 5: valorFilme = 0.0; break;	
			}
			
			valorTotal += valorFilme;
		}
		
		locacao.setValor(valorTotal);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		
		dataEntrega = adicionarDias(dataEntrega, 1);
		
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar metodo para salvar
		
		dao.salvar(locacao);
		
		return locacao;
	}
	
	public void setLocacaoDAO(LocacaoDAO dao) {
		this.dao = dao;
	}

	public void setSpcService(SPCService spcService) {
		this.spcService = spcService;
	}
}