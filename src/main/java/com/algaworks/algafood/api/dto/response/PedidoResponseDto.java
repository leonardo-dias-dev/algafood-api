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
public class PedidoResponseDto {
	
	private String codigo;
	
	private BigDecimal subtotal;
	
	private BigDecimal taxaFrete;
	
	private BigDecimal valorTotal;
	
	private OffsetDateTime dataCriacao;

	private OffsetDateTime dataConfirmacao;
	
	private OffsetDateTime dataCancelamento;
	
	private OffsetDateTime dataEntrega;
	
	private StatusPedido status = StatusPedido.CRIADO;
	
	private EnderecoResponseDto endereco;
	
	private FormaPagamentoResponseDto formaPagamento;

	private RestauranteResumoResponseDto restaurante;
	
	private UsuarioResponseDto cliente;
	
	private List<ItemPedidoResponseDto> itens = new ArrayList<>();
	
}
