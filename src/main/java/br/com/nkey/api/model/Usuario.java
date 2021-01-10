package br.com.nkey.api.model;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Transient
    public static final String SEQUENCE_NAME = "seq_usuario";
    
    @Id
    private long usuarioId;
	
    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 3, max = 100, message = "O tamanho mínimo é 3 o máximo é 100")
    private String usuarioNome;
    
    @NotBlank(message = "E-mail não pode ser vazio")
    @Size(min = 3, max = 100, message = "O tamanho mínimo é 3 o máximo é 100")
	@Email(message = "E-mail inválido")    
    private String usuarioEmail;

}
