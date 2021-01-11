package br.com.nkey.api.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import br.com.nkey.api.service.EventoService;
import br.com.nkey.api.service.sequence.generate.GeneratedSequence;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin()
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
	})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/eventos")
	public Evento criar(@Valid @RequestBody Evento evento) {
		evento.setEventoId(sequenceGeneratorService.generateSequence(Evento.SEQUENCE_NAME));
		return eventoRepository.save(evento);
	}
	
	@ApiOperation("Atualiza um evento.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Evento atualizado com sucesso."),
	})
	@PutMapping("/eventos/{id}")
	public ResponseEntity<Evento> atualizar(@PathVariable Long id, @Valid @RequestBody Evento evento) {
		Evento eventoSalvo = eventoService.atualizar(id, evento);
		return ResponseEntity.ok(eventoSalvo);
	}

	@ApiOperation("Remove um evento.")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Evento deletado com sucesso"),
		@ApiResponse(code = 404, message = "Evento não encontrado"),
	})
	@DeleteMapping("/eventos/{id}")
	public ResponseEntity<Evento> remover(@Valid @PathVariable Long id) {
		Optional<Evento> usuario = eventoRepository.findById(id);
		return usuario.isPresent() ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
	
	@ApiOperation("Remove todos os eventos")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Todos os eventos deletados com sucesso"),
	})
	@DeleteMapping("/eventos/remover/todos")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover() {
		eventoRepository.deleteAll();		
	}

}
