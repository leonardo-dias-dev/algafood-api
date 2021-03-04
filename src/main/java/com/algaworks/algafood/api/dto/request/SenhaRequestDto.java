package com.algaworks.algafood.api.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaRequestDto {
	
	@NotBlank
	private String senhaAtual;
	
	@NotBlank
	private String novaSenha;
	
}
