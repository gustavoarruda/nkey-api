package br.com.nkey.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.nkey.api.model.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, Long> {

}