package com.algaworks.algafood.api.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResponse {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Uberlândia")
	private String nome;
	
	private EstadoResponse estado;
	
}
