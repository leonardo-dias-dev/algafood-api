package com.algaworks.algafood.api.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoRequest {

	@NotBlank
	private String descricao;
	
}
