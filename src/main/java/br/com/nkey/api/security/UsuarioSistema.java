package br.com.nkey.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.nkey.api.model.Usuario;


public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;

	private Usuario usuarioSistema;

	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getUsuarioEmail(), usuario.getUsuarioSenha(), authorities);
		this.usuarioSistema = usuario;
	}

	public Usuario getUsuarioSistema() {
		return usuarioSistema;
	}
	
}
