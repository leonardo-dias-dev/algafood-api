package com.algaworks.algafood.api.dto.request;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoRequest {
	
	@NotBlank
	@ApiModelProperty(example = "Gerente", required = true)
	private String nome;
	
}
