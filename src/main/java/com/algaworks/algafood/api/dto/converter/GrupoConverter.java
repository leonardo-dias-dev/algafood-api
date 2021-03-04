package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.request.GrupoRequestDto;
import com.algaworks.algafood.api.dto.response.GrupoResponseDto;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoConverter extends AbstractConverter<Grupo, GrupoResponseDto, GrupoRequestDto> {
	
}
