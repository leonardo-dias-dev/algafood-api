package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.request.EstadoRequestDto;
import com.algaworks.algafood.api.dto.response.EstadoResponseDto;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoConverter extends AbstractConverter<Estado, EstadoResponseDto, EstadoRequestDto> {
	
}
