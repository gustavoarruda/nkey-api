package br.com.nkey.api.jwt;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.nkey.api.model.Usuario;
import br.com.nkey.api.repository.UsuarioRepository;
import br.com.nkey.api.security.UsuarioSistema;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String usuarioemail) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByUsuarioEmail(usuarioemail);
		if (!usuario.isPresent()) {
			throw new UsernameNotFoundException("Usuário não encontrado para este e-mail: " + usuarioemail);
		}

		return new UsuarioSistema(usuario.get(), new ArrayList<>());

	}

}
