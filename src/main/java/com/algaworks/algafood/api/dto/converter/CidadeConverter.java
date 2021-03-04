package com.algaworks.algafood.api.dto.converter;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.request.CidadeRequestDto;
import com.algaworks.algafood.api.dto.response.CidadeResponseDto;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeConverter extends AbstractConverter<Cidade, CidadeResponseDto, CidadeRequestDto> {
	
	@Override
	public void copyToEntity(CidadeRequestDto requestDto, Cidade entity) {
		/*
		 * Para evitar exception:
		 * 
		 * org.hibernate.HibernateException: identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
		 */
		entity.setEstado(new Estado());
		
		super.copyToEntity(requestDto, entity);
	}
	
}
