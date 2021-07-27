package com.algaworks.algafood.api.dto.request;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoRequest {

	@NotBlank
	@ApiModelProperty(example = "Cartão de crédito", required = true)
	private String descricao;
	
}
