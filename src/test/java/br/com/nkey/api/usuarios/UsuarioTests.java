package br.com.nkey.api.usuarios;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.nkey.api.jwt.JwtResponse;
import br.com.nkey.api.model.Usuario;
import br.com.nkey.api.resource.UsuarioResource;
import br.com.nkey.api.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class UsuarioTests {

//	@Autowired
//	private UsuarioResource resource;
//
//	@Autowired
//	private UsuarioService usuarioService;
//
//	private final Faker faker = new Faker(new Locale("pt", "BR"));
//
//	private final ObjectMapper mapper = new ObjectMapper();
//
//	@Autowired
//	private MockMvc mvc;
//
//	private final String name = "Gustavo Arruda";
//	private final String email = "gustavo@gmail.com";
//	private final String password = "password";
//
//	private String token;
//
//	@BeforeEach
//	public void autenticar() throws Exception {
//
//		removerUsuarios();
//
//		criarUsuario();
//
//		Usuario usuario = new Usuario();
//
//		usuario.setUsuarioEmail(email);
//		usuario.setUsuarioSenha(password);
//
//		String jsonToken = mvc.perform(
//				post("/api/v1/authenticate")
//				.content(mapper.writeValueAsBytes(usuario))
//				.contentType(MediaType.APPLICATION_JSON))
//				.andReturn()
//				.getResponse()
//				.getContentAsString();
//
//		token = mapper.readValue(jsonToken, JwtResponse.class).getToken();
//
//	}
//	
//	@Test
//	public void deveCriarUmUsuario() throws Exception {
//
//		final Usuario usuario = usuario();
//
//		mvc.perform(
//				post("/api/v1/usuarios")
//					.content(mapper.writeValueAsBytes(usuario))
//					.header("Authorization", "Bearer " + token)
//					.contentType(MediaType.APPLICATION_JSON))
//					.andExpect(status().isCreated())
//					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//					.andExpect(jsonPath("$.usuarioNome", is(usuario.getUsuarioNome())))					
//					.andExpect(jsonPath("$.usuarioEmail",is(usuario.getUsuarioEmail())));
//	}
//
//	@Test
//	public void naoDeveCriarUmUsuarioSemNome() throws Exception {
//		
//		final Usuario usuario = usuario();
//		
//		usuario.setUsuarioNome(null);
//
//		mvc.perform(
//				post("/api/v1/usuarios")
//					.content(mapper.writeValueAsBytes(usuario))
//					.header("Authorization", "Bearer " + token)
//					.contentType(MediaType.APPLICATION_JSON))
//					.andExpect(status().isBadRequest())
//					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
//	}
//
//	@Test
//	public void naoDeveCriarUmUsuarioSemEmail() throws Exception {
//
//		final Usuario usuario = usuario();
//		
//		usuario.setUsuarioEmail(null);
//
//		mvc.perform(
//				post("/api/v1/usuarios")
//					.content(mapper.writeValueAsBytes(usuario))
//					.header("Authorization", "Bearer " + token)
//					.contentType(MediaType.APPLICATION_JSON))
//					.andExpect(status().isBadRequest())
//					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
//	}
//
//	@Test
//	public void deveDeletarUmUsuario() throws Exception {
//
//		final Usuario usuario = resource.criar(usuario());
//
//		mvc.perform(
//				delete("/api/v1/usuarios/" + usuario.getUsuarioId())
//				.header("Authorization", "Bearer " + token)
//				.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isNoContent());
//	}
//	
//	private Usuario usuario() {
//
//		final Usuario usuario = Usuario
//				.builder()
//				.usuarioNome(faker.name().fullName())
//				.usuarioEmail(faker.internet().emailAddress())
//				.build();
//		return usuario;
//
//	}
//	
//	public void criarUsuario() {
//
//		final Usuario usuario = Usuario
//				.builder()
//					.usuarioNome(name)
//					.usuarioEmail(email)
//					.usuarioSenha(password)
//				.build();
//		usuarioService.criar(usuario);
//
//	}
//
//	public void removerUsuarios() {		
//		usuarioService.removerTodos();
//	}
	
}
