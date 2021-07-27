package com.algaworks.algafood.api.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.algaworks.algafood.domain.model.StatusPedido;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResumoResponse {

	@ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
	private String codigo;

	@ApiModelProperty(example = "298.90")
	private BigDecimal subtotal;

	@ApiModelProperty(example = "10.00")
	private BigDecimal taxaFrete;

	@ApiModelProperty(example = "308.90")
	private BigDecimal valorTotal;

	@ApiModelProperty(example = "")
	private OffsetDateTime dataCriacao;

	@ApiModelProperty(example = "CRIADO")
	private StatusPedido status = StatusPedido.CRIADO;

	private RestauranteResumoResponse restaurante;

	private UsuarioResponse cliente;
	
}
