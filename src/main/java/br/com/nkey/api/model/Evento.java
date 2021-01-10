package br.com.nkey.api.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "eventos")
public @Data class Evento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	public static final String SEQUENCE_NAME = "seq_evento";

	@Id
	private long eventoId;

	@NotBlank
	@Size(min = 3, max = 100)
	private String eventoNome;

	@NotBlank
	private String eventoData;

	private Usuario usuario;
	
}
