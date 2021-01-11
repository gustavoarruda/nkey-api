package br.com.nkey.api.eventos;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import br.com.nkey.api.model.Evento;
import br.com.nkey.api.model.Usuario;
import br.com.nkey.api.resource.EventoResource;
import br.com.nkey.api.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class EventoTests {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EventoResource resource;

	private final Faker faker = new Faker(new Locale("pt", "BR"));

	private final ObjectMapper mapper = new ObjectMapper();

	private final String name = "Gustavo Arruda";
	private final String email = "gustavo@gmail.com";
	private final String password = "password";
	
	private String token;
	
	@Autowired
	private MockMvc mvc;
	
	@BeforeEach
	public void autenticar() throws Exception {
		
		removerUsuarios();
		
		criarUsuario();
		
		Usuario usuario = new Usuario();
		
		usuario.setUsuarioEmail(email);
		usuario.setUsuarioSenha(password);
		
		String jsonToken = mvc.perform(
				post("/api/v1/authenticate")
				.content(mapper.writeValueAsBytes(usuario))
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse()
				.getContentAsString();

		token = mapper.readValue(jsonToken, JwtResponse.class).getToken();		
		
	}
	
	@Test
	public void deveCriarUmEvento() throws Exception {

		final Evento evento = evento();

		mvc.perform(
				post("/api/v1/eventos")
					.content(mapper.writeValueAsBytes(evento))
					.header("Authorization", "Bearer " + token)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.eventoNome", is(evento.getEventoNome())))					
					.andExpect(jsonPath("$.eventoData",is(evento.getEventoData())));

	}
	
	@Test
	public void naoDeveCriarUmEventoSemNome() throws Exception {
		
		final Evento evento = evento();
		
		evento.setEventoNome(null);

		mvc.perform(
				post("/api/v1/eventos")
					.content(mapper.writeValueAsBytes(evento))
					.header("Authorization", "Bearer " + token)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void naoDeveCriarUmEventoSemData() throws Exception {

		final Evento evento = evento();

		evento.setEventoData(null);

		mvc.perform(
				post("/api/v1/eventos")
					.content(mapper.writeValueAsBytes(evento))
					.header("Authorization", "Bearer " + token)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void deveDeletarUmEvento() throws Exception {

		final Evento evento = resource.criar(evento());

		mvc.perform(
				delete("/api/v1/eventos/" + evento.getEventoId())
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	private Evento evento() throws ParseException {		

		Usuario usuario = new Usuario();
		
		usuario.setUsuarioNome(faker.name().fullName());
		usuario.setUsuarioEmail(faker.internet().emailAddress());
		usuario.setUsuarioSenha(password);
		usuario = usuarioService.criarUsuarioFaker(usuario);		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = sdf.format(new Date());

		final Evento evento = Evento
				.builder()
					.eventoNome(faker.name().fullName())
					.eventoData(dateString)
					.usuario(usuario)
				.build();
		return evento;

	}

	public void criarUsuario() {

		final Usuario usuario = Usuario
				.builder()
					.usuarioNome(name)
					.usuarioEmail(email)
					.usuarioSenha(password)
				.build();
		usuarioService.criar(usuario);

	}

	public void removerUsuarios() {		
		usuarioService.removerTodos();
	}

}
