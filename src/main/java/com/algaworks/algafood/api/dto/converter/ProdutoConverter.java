package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.request.ProdutoRequest;
import com.algaworks.algafood.api.dto.response.ProdutoResponse;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoConverter extends AbstractConverter<Produto, ProdutoResponse, ProdutoRequest> {

}
