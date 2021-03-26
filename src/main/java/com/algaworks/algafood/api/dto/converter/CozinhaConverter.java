package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.request.CozinhaRequest;
import com.algaworks.algafood.api.dto.response.CozinhaResponse;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaConverter extends AbstractConverter<Cozinha, CozinhaResponse, CozinhaRequest> {
	
}
