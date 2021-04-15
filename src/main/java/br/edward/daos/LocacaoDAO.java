package br.edward.daos;

import java.util.List;

import br.edward.entidades.Locacao;

public interface LocacaoDAO {
	
	public void salvar(Locacao locacao);

	public List<Locacao> obterLocacoesPendentes();
}
