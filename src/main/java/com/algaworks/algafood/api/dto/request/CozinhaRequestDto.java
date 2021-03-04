package com.algaworks.algafood.api.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaRequestDto {
	
	@NotBlank
	private String nome;
	
}
