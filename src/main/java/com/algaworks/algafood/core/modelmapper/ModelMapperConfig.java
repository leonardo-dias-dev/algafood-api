package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.dto.response.EnderecoResponse;
import com.algaworks.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(Endereco.class, EnderecoResponse.class)
			.<String>addMapping(endereco -> endereco.getCidade().getEstado().getNome(), (enderecoDto, value) -> enderecoDto.getCidade().setEstado(value));
		
		return modelMapper;
	}

}
