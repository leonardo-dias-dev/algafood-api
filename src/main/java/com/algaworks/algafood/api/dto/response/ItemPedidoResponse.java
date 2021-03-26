package com.algaworks.algafood.api.dto.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoResponse {
	
	private Integer quantidade;
	
	private BigDecimal precoUnitario;
	
	private BigDecimal precoTotal;
	
	private String observacao;
	
	private Long produtoId;
	
	private String produtoNome;
	
}
