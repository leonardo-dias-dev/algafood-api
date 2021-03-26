package com.algaworks.algafood.api.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.domain.model.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResponse {
	
	private String codigo;
	
	private BigDecimal subtotal;
	
	private BigDecimal taxaFrete;
	
	private BigDecimal valorTotal;
	
	private OffsetDateTime dataCriacao;

	private OffsetDateTime dataConfirmacao;
	
	private OffsetDateTime dataCancelamento;
	
	private OffsetDateTime dataEntrega;
	
	private StatusPedido status = StatusPedido.CRIADO;
	
	private EnderecoResponse endereco;
	
	private FormaPagamentoResponse formaPagamento;

	private RestauranteResumoResponse restaurante;
	
	private UsuarioResponse cliente;
	
	private List<ItemPedidoResponse> itens = new ArrayList<>();
	
}
