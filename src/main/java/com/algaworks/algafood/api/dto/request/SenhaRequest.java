package com.algaworks.algafood.api.dto.request;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaRequest {
	
	@NotBlank
	@ApiModelProperty(example = "123456", required = true)
	private String senhaAtual;
	
	@NotBlank
	@ApiModelProperty(example = "123456", required = true)
	private String novaSenha;
	
}
