package br.com.nkey.api.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.nkey.api.model.Evento;
import br.com.nkey.api.repository.EventoRepository;
import br.com.nkey.api.sequence.generate.GeneratedSequence;
import br.com.nkey.api.service.EventoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Eventos", description = "Manipulação de Eventos")
@Transactional
public class EventoResource {

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private EventoService eventoService;

	@Autowired
	private GeneratedSequence sequenceGeneratorService;

	@ApiOperation("Lista todos os eventos.")
	@GetMapping("/eventos")
	public List<Evento> listar() {
		return eventoRepository.findAll(Sort.by(Sort.Direction.ASC, "eventoId"));
	}


	@ApiOperation("Retorna um evento pelo seu id.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Evento retornado com sucesso."),
		@ApiResponse(code = 404, message = "Evento não encontrado.")
	})
	@GetMapping("/eventos/{id}")
	public ResponseEntity<Optional<Evento>> getEventoByEventoId(@Valid @PathVariable Long id) {
		System.out.println("evto: " + id.toString());
		Optional<Evento> evento = eventoRepository.findById(id);
		return evento.isPresent() ? ResponseEntity.ok(evento) : ResponseEntity.notFound().build();

	}

	@ApiOperation("Cria um evento.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Evento criado com sucesso."),
		@ApiResponse(code = 422, message = "Dados inválidos")
	})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/eventos")
	public Evento criar(@Valid @RequestBody Evento evento) {
		evento.setEventoId(sequenceGeneratorService.generateSequence(Evento.SEQUENCE_NAME));
		return eventoRepository.save(evento);
	}
	
	@ApiOperation("Agendar um evento para um usuário")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Evento criado com sucesso."),
		@ApiResponse(code = 422, message = "Dados inválidos")
	})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/eventos/agendar/{usuario}/{data}")
	public Evento agendar(@Valid @RequestBody Evento evento) {
		evento.setEventoId(sequenceGeneratorService.generateSequence(Evento.SEQUENCE_NAME));
		return eventoRepository.save(evento);
	}

	@ApiOperation("Atualiza um evento.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Evento atualizado com sucesso."),
		@ApiResponse(code = 422, message = "Dados inválidos"),
		@ApiResponse(code = 401, message = "Não autorizado"),
		@ApiResponse(code = 403, message = "Operação não permitida"),
		@ApiResponse(code = 404, message = "Evento não encontrado"),
	})
	@PutMapping("/eventos/{id}")
	public ResponseEntity<Evento> atualizar(@PathVariable Long id, @Valid @RequestBody Evento evento) {
		Evento eventoSalvo = eventoService.atualizar(id, evento);
		return ResponseEntity.ok(eventoSalvo);
	}

	@ApiOperation("Remove um evento.")
	@DeleteMapping("/eventos/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		eventoRepository.deleteById(id);
	}

}
