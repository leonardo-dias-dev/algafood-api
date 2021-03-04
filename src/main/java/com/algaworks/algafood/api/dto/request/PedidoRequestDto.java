package com.algaworks.algafood.api.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoRequestDto {
	
	@Valid
	@NotNull
	private RestauranteIdRequestDto restaurante;

	@Valid
	@NotNull
	private FormaPagamentoIdRequestDto formaPagamento;
	
	@Valid
	@NotNull
	private EnderecoRequestDto endereco;
	
	@Valid
	@NotNull
	@Size(min = 1)
	private List<ItemPedidoRequestDto> itens;
	
}
