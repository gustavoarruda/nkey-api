package br.com.nkey.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.nkey.api.model.Evento;
import br.com.nkey.api.repository.EventoRepository;

@Service
public class EventoService {

	@Autowired
	private EventoRepository eventoRepository;

	public Evento atualizar(Long eventoId, Evento evento) {
		
		Optional<Evento> eventoEncontrado = buscarEventoPeloId(eventoId);
		Evento eventoAtualizar = new Evento();
		
		eventoEncontrado.ifPresent(ev -> {
			eventoAtualizar.setEventoId(ev.getEventoId());
			eventoAtualizar.setEventoNome(ev.getEventoNome());
			eventoAtualizar.setEventoData(ev.getEventoData());
		});		

		BeanUtils.copyProperties(evento, eventoAtualizar);
		return eventoRepository.save(eventoAtualizar);
	}
	
	public Optional<Evento> buscarEventoPeloId(Long eventoId) {
		Optional<Evento> eventoEncontrado = eventoRepository.findById(eventoId);
		if (!eventoEncontrado.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return eventoEncontrado;
	}
}
