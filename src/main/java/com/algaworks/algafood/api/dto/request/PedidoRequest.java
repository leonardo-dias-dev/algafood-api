package com.algaworks.algafood.api.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoRequest {
	
	@Valid
	@NotNull
	private RestauranteIdRequest restaurante;

	@Valid
	@NotNull
	private FormaPagamentoIdRequest formaPagamento;
	
	@Valid
	@NotNull
	private EnderecoRequest endereco;
	
	@Valid
	@NotNull
	@Size(min = 1)
	private List<ItemPedidoRequest> itens;
	
}
