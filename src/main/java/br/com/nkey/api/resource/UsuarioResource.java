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

import br.com.nkey.api.model.Usuario;
import br.com.nkey.api.repository.UsuarioRepository;
import br.com.nkey.api.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin()
@RestController
@RequestMapping("/api/v1")
@Api(tags = "Usuários", description = "Manipulação de Usuários")
@Transactional
public class UsuarioResource {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
	@Autowired
	private UsuarioService usuarioService;

	@ApiOperation("Lista todos os usuários.")
    @GetMapping("/usuarios")
    public List<Usuario> listar() {
        return usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "usuarioId"));
    }
	
	@ApiOperation("Retorna um usuário pelo seu id.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Usuário retornado com sucesso."),
		@ApiResponse(code = 404, message = "Usuário não encontrado.")
	})
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Optional<Usuario>> getusuarioByusuarioId(@PathVariable Long id) {
    	Optional<Usuario> usuario = usuarioRepository.findById(id);
    	return usuario.isPresent() ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }
    
	@ApiOperation("Cria um usuário.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Usuário criado com sucesso."),
	})
    @PostMapping("/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
    public Usuario criar(@Valid @RequestBody Usuario usuario) {
        return usuarioService.criar(usuario);
    }
    
	@ApiOperation("Atualiza um usuário.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Usuário atualizado com sucesso."),
		@ApiResponse(code = 404, message = "Usuário não encontrado"),
	})
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
		Usuario usuarioSalvo = usuarioService.atualizar(id, usuario);
		return ResponseEntity.ok(usuarioSalvo);
	}
    
	@ApiOperation("Remove um usuário.")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Usuário deletado com sucesso"),
		@ApiResponse(code = 404, message = "Usuário não encontrado"),
	})
	@DeleteMapping("/usuarios/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@Valid @PathVariable Long id) {
		usuarioService.remover(id);		
	}
	
	@ApiOperation("Remove todos os usuários")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Todos os usuários deletados com sucesso"),
	})
	@DeleteMapping("/usuarios/remover/todos")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerTodos() {
		usuarioRepository.deleteAll();		
	}
}
	
