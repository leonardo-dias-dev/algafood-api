package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.request.FormaPagamentoRequest;
import com.algaworks.algafood.api.dto.response.FormaPagamentoResponse;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoConverter extends AbstractConverter<FormaPagamento, FormaPagamentoResponse, FormaPagamentoRequest> {
	
}
