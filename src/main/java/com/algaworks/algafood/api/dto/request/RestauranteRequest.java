package com.algaworks.algafood.api.dto.request;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.core.validation.annotation.TaxaFrete;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteRequest {
	
	@NotBlank
	private String nome;
	
	@NotNull
	@TaxaFrete
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaIdRequest cozinha;

	@Valid
	@NotNull
	private EnderecoRequest endereco;
	
}
