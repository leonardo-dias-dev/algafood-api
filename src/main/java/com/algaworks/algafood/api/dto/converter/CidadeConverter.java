package com.algaworks.algafood.api.dto.converter;

import com.algaworks.algafood.api.dto.request.CidadeRequest;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.response.CidadeResponse;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeConverter extends AbstractConverter<Cidade, CidadeResponse, CidadeRequest> {
	
	@Override
	public void copyToEntity(CidadeRequest requestDto, Cidade entity) {
		/*
		 * Para evitar exception:
		 * 
		 * org.hibernate.HibernateException: identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
		 */
		entity.setEstado(new Estado());
		
		super.copyToEntity(requestDto, entity);
	}
	
}
