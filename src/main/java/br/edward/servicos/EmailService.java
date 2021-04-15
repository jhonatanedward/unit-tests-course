package br.edward.servicos;

import br.edward.entidades.Usuario;

public interface EmailService {
	public void notificarAtraso(Usuario usuario);
}
