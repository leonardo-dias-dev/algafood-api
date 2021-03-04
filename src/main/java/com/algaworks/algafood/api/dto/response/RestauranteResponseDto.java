package com.algaworks.algafood.api.dto.response;

import java.math.BigDecimal;

import com.algaworks.algafood.api.dto.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteResponseDto {
	
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private Long id;
	
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private String nome;

	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	
	private Boolean ativo;
	
	private Boolean aberto;

	@JsonView(RestauranteView.Resumo.class)
	private CozinhaResponseDto cozinha;
	
	private EnderecoResponseDto endereco;
	
}
