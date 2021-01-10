package br.com.nkey.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.nkey.api.model.Usuario;
import br.com.nkey.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario atualizar(Long usuarioId, Usuario usuario) {
		
		Optional<Usuario> usuarioEncontrado = buscarUsuarioPeloId(usuarioId);
		Usuario usuarioAtualizar = new Usuario();
		
		usuarioEncontrado.ifPresent(ev -> {
			usuarioAtualizar.setUsuarioId(ev.getUsuarioId());
			usuarioAtualizar.setUsuarioNome(ev.getUsuarioNome());
			usuarioAtualizar.setUsuarioEmail(ev.getUsuarioEmail());
		});
		
		BeanUtils.copyProperties(usuario, usuarioAtualizar, null, null);
		return usuarioRepository.save(usuarioAtualizar);
	}
	
	public Optional<Usuario> buscarUsuarioPeloId(Long usuarioId) {
		Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(usuarioId);
		if (!usuarioEncontrado.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return usuarioEncontrado;
	}
	
}
