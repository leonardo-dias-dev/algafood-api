package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.request.PedidoRequestDto;
import com.algaworks.algafood.api.dto.response.PedidoResponseDto;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoConverter extends AbstractConverter<Pedido, PedidoResponseDto, PedidoRequestDto> {

}
