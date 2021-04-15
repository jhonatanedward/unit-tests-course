package br.edward.builders;

import br.edward.entidades.Usuario;

public class UsuarioBuilder {
	
	private Usuario usuario;
	
	// Privado para que ninguem externo a essa classe possa criar um UsuarioBuilder
	private UsuarioBuilder() {}
	
	public static UsuarioBuilder umUsuario() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		builder.usuario.setNome("Usuario 1");
		return builder;
	}
	
	public UsuarioBuilder comNome(String nome) {
		usuario.setNome(nome);
		return this;
	}
	
	public Usuario agora() {
		return usuario;
	}
}
