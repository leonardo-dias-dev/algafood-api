package com.algaworks.algafood.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoResponse {
	
	private String cep;
	
	private String logradouro;
	
	private String numero;
	
	private String complemento;
	
	private String bairro;
	
	private CidadeResumoResponse cidade;
	
}
