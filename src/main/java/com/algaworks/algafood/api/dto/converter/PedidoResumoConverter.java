package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.response.PedidoResumoResponse;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoConverter extends AbstractConverter<Pedido, PedidoResumoResponse, Object> {

}
