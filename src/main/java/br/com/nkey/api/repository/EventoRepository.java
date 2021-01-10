package br.com.nkey.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.nkey.api.model.Evento;

public interface EventoRepository extends MongoRepository<Evento, Long> {


}
