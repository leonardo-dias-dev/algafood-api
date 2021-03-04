package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.request.RestauranteRequestDto;
import com.algaworks.algafood.api.dto.response.RestauranteResponseDto;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteConverter extends AbstractConverter<Restaurante, RestauranteResponseDto, RestauranteRequestDto> {
	
	@Override
	public void copyToEntity(RestauranteRequestDto requestDto, Restaurante entity) {
		/*
		 * Para evitar exception:
		 * 
		 * org.hibernate.HibernateException: identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
		 */
		entity.setCozinha(new Cozinha());
		
		if (entity.getEndereco() != null) {
			entity.getEndereco().setCidade(new Cidade());
		}
		
		super.copyToEntity(requestDto, entity);
	}
	
}
