package com.algaworks.algafood.api.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSemSenhaRequest {
	
	@NotBlank
	private String nome;
	
	@Email
	@NotBlank
	private String email;
	
}
