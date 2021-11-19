package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.dto.model.EnderecoModel;
import com.algaworks.algafood.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Endereco.class, EnderecoModel.class)
                .<String>addMapping(endereco -> endereco.getCidade().getEstado().getNome(), (enderecoDto, value) -> enderecoDto.getCidade().setEstado(value));

        return modelMapper;
    }

}
