package br.com.nkey.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.nkey.api.jwt.JwtResponse;
import br.com.nkey.api.jwt.JwtUserDetailsService;
import br.com.nkey.api.jwt.util.JwtTokenUtil;
import br.com.nkey.api.model.Usuario;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class JwtAuthenticationResource {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	// TODO: criar classe para receber usuario/senha
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Usuario authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsuarioEmail(), authenticationRequest.getUsuarioSenha());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsuarioEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String usuarioEmail, String usuarioSenha) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioEmail, usuarioSenha));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
