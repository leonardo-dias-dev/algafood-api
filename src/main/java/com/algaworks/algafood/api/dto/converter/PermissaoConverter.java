package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.response.PermissaoResponseDto;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoConverter extends AbstractConverter<Permissao, PermissaoResponseDto, Object> {

}
