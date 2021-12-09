package com.algaworks.algafood.api.v2.converter;

import com.algaworks.algafood.api.v2.AlgaLinksV2;
import com.algaworks.algafood.api.v2.controller.CozinhaControllerV2;
import com.algaworks.algafood.api.v2.dto.input.CozinhaInputV2;
import com.algaworks.algafood.api.v2.dto.model.CozinhaModelV2;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaConverterV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinksV2 algaLinksV2;

    public CozinhaConverterV2() {
        super(CozinhaControllerV2.class, CozinhaModelV2.class);
    }

    @Override
    public CozinhaModelV2 toModel(Cozinha entity) {
        CozinhaModelV2 cozinhaModel = createModelWithId(entity.getId(), entity);

        modelMapper.map(entity, cozinhaModel);

        cozinhaModel.add(algaLinksV2.linkToCozinhas("cozinhas"));

        return cozinhaModel;
    }

    public Cozinha toDomainObject(CozinhaInputV2 cozinhaInputV2) {
        return modelMapper.map(cozinhaInputV2, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInputV2 cozinhaInputV2, Cozinha cozinha) {
        modelMapper.map(cozinhaInputV2, cozinha);
    }

}
