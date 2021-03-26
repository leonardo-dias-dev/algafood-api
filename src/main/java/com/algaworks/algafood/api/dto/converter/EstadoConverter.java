package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.request.EstadoRequest;
import com.algaworks.algafood.api.dto.response.EstadoResponse;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoConverter extends AbstractConverter<Estado, EstadoResponse, EstadoRequest> {
	
}
