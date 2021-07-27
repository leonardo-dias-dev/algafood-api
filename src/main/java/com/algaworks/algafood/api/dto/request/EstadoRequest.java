package com.algaworks.algafood.api.dto.request;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoRequest {
	
	@NotBlank
	@ApiModelProperty(example = "Mato Grosso", required = true)
	private String nome;
	
}
