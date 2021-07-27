package com.algaworks.algafood.api.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSemSenhaRequest {
	
	@NotBlank
	@ApiModelProperty(example = "José da Silva", required = true)
	private String nome;
	
	@Email
	@NotBlank
	@ApiModelProperty(example = "jose.silva@algafood.com.br", required = true)
	private String email;
	
}
