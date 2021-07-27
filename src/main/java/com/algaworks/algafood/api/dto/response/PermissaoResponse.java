package com.algaworks.algafood.api.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoResponse {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "CONSULTAR_COZINHAS")
	private String nome;

	@ApiModelProperty(example = "Permite consultar cozinhas")
	private String descricao;
	
}
