package com.algaworks.algafood.api.v2.converter;

import com.algaworks.algafood.api.v2.AlgaLinksV2;
import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;
import com.algaworks.algafood.api.v2.dto.input.CidadeInputV2;
import com.algaworks.algafood.api.v2.dto.model.CidadeModelV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeConverterV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinksV2 algaLinksV2;

    public CidadeConverterV2() {
        super(CidadeControllerV2.class, CidadeModelV2.class);
    }

    @Override
    public CidadeModelV2 toModel(Cidade entity) {
        CidadeModelV2 cidadeModel = createModelWithId(entity.getId(), entity);

        modelMapper.map(entity, cidadeModel);

        cidadeModel.add(algaLinksV2.linkToCidades("cidades"));

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinksV2.linkToCidades());
    }

    public Cidade toDomainObject(CidadeInputV2 cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInputV2 cidadeInput, Cidade cidade) {
        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // com.algaworks.algafood.domain.model.Estado was altered from 1 to 2
        cidade.setEstado(new Estado());

        modelMapper.map(cidadeInput, cidade);
    }

}
