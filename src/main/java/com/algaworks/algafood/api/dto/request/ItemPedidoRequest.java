package com.algaworks.algafood.api.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoRequest {
	
	@NotNull
	@PositiveOrZero
	private Integer quantidade;
	
	private String observacao;
	
	private ProdutoIdRequest produto;
	
}
