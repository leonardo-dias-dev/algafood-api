package com.algaworks.algafood.api.dto.request;

import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
@Setter
public class CidadeRequestDto {
	
	@NotBlank
	private String nome;
	
	@Valid
	@NotNull
	private EstadoIdRequestDto estado;
	
}
