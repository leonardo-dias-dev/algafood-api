package com.algaworks.algafood.api.dto.converter;

import com.algaworks.algafood.api.dto.request.GrupoRequest;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.response.GrupoResponse;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoConverter extends AbstractConverter<Grupo, GrupoResponse, GrupoRequest> {
	
}
