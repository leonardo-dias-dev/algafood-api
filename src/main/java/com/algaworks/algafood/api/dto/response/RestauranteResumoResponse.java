package com.algaworks.algafood.api.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteResumoResponse {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Thai Gourmet")
	private String nome;
	
}
