package com.algaworks.algafood.api.dto.request;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.core.validation.annotation.TaxaFrete;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteRequest {
	
	@NotBlank
	@ApiModelProperty(example = "Thai Gourmet", required = true)
	private String nome;
	
	@NotNull
	@TaxaFrete
	@ApiModelProperty(example = "12.50", required = true)
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaIdRequest cozinha;

	@Valid
	@NotNull
	private EnderecoRequest endereco;
	
}
