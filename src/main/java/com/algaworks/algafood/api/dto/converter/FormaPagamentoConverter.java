package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.request.FormaPagamentoRequestDto;
import com.algaworks.algafood.api.dto.response.FormaPagamentoResponseDto;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoConverter extends AbstractConverter<FormaPagamento, FormaPagamentoResponseDto, FormaPagamentoRequestDto> {
	
}
