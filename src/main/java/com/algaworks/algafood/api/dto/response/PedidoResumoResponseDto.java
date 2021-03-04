package com.algaworks.algafood.api.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.algaworks.algafood.domain.model.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResumoResponseDto {
	
	private String codigo;
	
	private BigDecimal subtotal;
	
	private BigDecimal taxaFrete;
	
	private BigDecimal valorTotal;
	
	private OffsetDateTime dataCriacao;

	private StatusPedido status = StatusPedido.CRIADO;
	
	private RestauranteResumoResponseDto restaurante;
	
	private UsuarioResponseDto cliente;
	
}
