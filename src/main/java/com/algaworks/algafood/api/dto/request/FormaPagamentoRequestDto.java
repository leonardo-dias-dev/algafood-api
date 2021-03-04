package com.algaworks.algafood.api.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoRequestDto {

	@NotBlank
	private String descricao;
	
}
