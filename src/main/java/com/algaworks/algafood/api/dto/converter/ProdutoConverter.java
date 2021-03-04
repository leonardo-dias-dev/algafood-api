package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.request.ProdutoRequestDto;
import com.algaworks.algafood.api.dto.response.ProdutoResponseDto;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoConverter extends AbstractConverter<Produto, ProdutoResponseDto, ProdutoRequestDto> {

}
