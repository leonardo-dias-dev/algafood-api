package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioComSenhaRequest extends UsuarioSemSenhaRequest {
	
	@NotBlank
	@ApiModelProperty(example = "123456", required = true)
	private String senha;
	
}
