package br.com.nkey.api.usuario;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

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

import br.com.nkey.api.model.Usuario;
import br.com.nkey.api.resource.UsuarioResource;


@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class UsuarioTests {

	@Autowired
	private UsuarioResource resource;
	
	private final Faker faker = new Faker(new Locale("pt", "BR"));
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void deveCriarUmUsuario() throws Exception {

		final Usuario usuario = usuario();

		mvc.perform(
				post("/api/v1/usuarios")
					.content(mapper.writeValueAsBytes(usuario))
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.usuarioNome", is(usuario.getUsuarioNome())))					
					.andExpect(jsonPath("$.usuarioEmail",is(usuario.getUsuarioEmail())));

	}
	
	@Test
	public void naoDeveCriarUmUsuarioSemNome() throws Exception {
		
		final Usuario usuario = usuario();
		
		usuario.setUsuarioNome(null);

		mvc.perform(
				post("/api/v1/usuarios")
					.content(mapper.writeValueAsBytes(usuario))
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void naoDeveCriarUmUsuarioSemEmail() throws Exception {

		final Usuario usuario = usuario();
		
		usuario.setUsuarioEmail(null);

		mvc.perform(
				post("/api/v1/usuarios")
					.content(mapper.writeValueAsBytes(usuario))
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void deveDeletarUmUsuario() throws Exception {

		final Usuario usuario = resource.criar(usuario());

		mvc.perform(
				delete("/api/v1/usuarios/" + usuario.getUsuarioId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	private Usuario usuario() {

		final Usuario usuario = Usuario
				.builder()
					.usuarioNome(faker.name().fullName())
					.usuarioEmail(faker.internet().emailAddress())					
				.build();
		return usuario;

	}
	
}
