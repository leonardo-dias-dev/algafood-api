package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.response.FotoProdutoResponseDto;
import com.algaworks.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoConverter extends AbstractConverter<FotoProduto, FotoProdutoResponseDto, Object> {

}
