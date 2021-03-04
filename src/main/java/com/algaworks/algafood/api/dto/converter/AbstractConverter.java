package com.algaworks.algafood.api.dto.converter;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractConverter<ENTITY, RESPONSE, REQUEST> {
	
	private Class<ENTITY> entityClass;
	
	private Class<RESPONSE> responseClass;
	
	@Autowired
	protected ModelMapper modelMapper;
	
	@SuppressWarnings("unchecked")
	public AbstractConverter() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		
		this.entityClass = (Class<ENTITY>) parameterizedType.getActualTypeArguments()[0];
		this.responseClass = (Class<RESPONSE>) parameterizedType.getActualTypeArguments()[1];
	}
	
	public RESPONSE toResponseDto(ENTITY entity) {
		return modelMapper.map(entity, responseClass);
	}
	
	public List<RESPONSE> toCollectionResponseDto(Collection<ENTITY> entities) {
		return entities.stream()
				.map(entity -> toResponseDto(entity))
				.collect(Collectors.toList());
	}
	
	public ENTITY toEntity(REQUEST requestDto) {
		return modelMapper.map(requestDto, entityClass);
	}
	
	public void copyToEntity(REQUEST requestDto, ENTITY entity) {
		modelMapper.map(requestDto, entity);
	}

}
