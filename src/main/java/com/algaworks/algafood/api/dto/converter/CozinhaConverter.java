package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.request.CozinhaRequestDto;
import com.algaworks.algafood.api.dto.response.CozinhaResponseDto;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaConverter extends AbstractConverter<Cozinha, CozinhaResponseDto, CozinhaRequestDto> {
	
}
