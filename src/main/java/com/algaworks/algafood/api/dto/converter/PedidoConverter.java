package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.request.PedidoRequest;
import com.algaworks.algafood.api.dto.response.PedidoResponse;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoConverter extends AbstractConverter<Pedido, PedidoResponse, PedidoRequest> {

}
