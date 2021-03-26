package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.response.UsuarioResponse;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioConverter extends AbstractConverter<Usuario, UsuarioResponse, Object> {
	
}
