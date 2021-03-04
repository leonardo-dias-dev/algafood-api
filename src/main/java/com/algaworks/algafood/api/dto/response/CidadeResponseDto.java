package com.algaworks.algafood.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResponseDto {
	
	private Long id;
	
	private String nome;
	
	private EstadoResponseDto estado;
	
}
