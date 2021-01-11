package br.com.nkey.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.nkey.api.model.Usuario;
import br.com.nkey.api.repository.UsuarioRepository;
import br.com.nkey.api.service.sequence.generate.GeneratedSequence;

@Service
public class UsuarioService {
	
    @Autowired
    private GeneratedSequence sequenceGeneratorService;
    
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario atualizar(Long usuarioId, Usuario usuario) {
		Usuario usuarioEncontrado = buscarUsuarioPeloId(usuarioId);
		BeanUtils.copyProperties(usuario, usuarioEncontrado, null, "usuarioSenha");
		return usuarioRepository.save(usuarioEncontrado);
	}
	
	public Usuario criar(Usuario usuario) {
    	usuario.setUsuarioId(sequenceGeneratorService.generateSequence(Usuario.SEQUENCE_NAME));
    	usuario.setUsuarioSenha(bcryptEncoder.encode(usuario.getUsuarioSenha()));
    	return usuarioRepository.save(usuario);
	}
	
	public Usuario criarUsuarioFaker(Usuario usuario) {
    	usuario.setUsuarioId(sequenceGeneratorService.generateSequence(Usuario.SEQUENCE_NAME));
    	usuario.setUsuarioSenha(bcryptEncoder.encode(usuario.getUsuarioSenha()));
    	return usuario;
	}
	
	
	public void remover( Long usuarioId ) {
		// TODO: imp remover
		// TODO: esconder a senha
	}
	
	public void removerTodos() {
		usuarioRepository.deleteAll();		
	}
	
	public Usuario buscarUsuarioPeloId(Long usuarioId) {
		return usuarioRepository.findById(usuarioId).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}
	
}
