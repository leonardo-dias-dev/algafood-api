package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.dto.input.EstadoInput;
import com.algaworks.algafood.api.v1.dto.model.EstadoModel;
import com.algaworks.algafood.core.security.resourceserver.AlgaSecurity;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EstadoConverter extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public EstadoConverter() {
        super(EstadoController.class, EstadoModel.class);
    }

    @Override
    public EstadoModel toModel(Estado entity) {
        EstadoModel estadoModel = createModelWithId(entity.getId(), entity);

        modelMapper.map(entity, estadoModel);

        if (algaSecurity.podeConsultarEstados()) {
            estadoModel.add(algaLinks.linkToEstados("estados"));
        }

        return estadoModel;
    }

    @Override
    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
        CollectionModel<EstadoModel> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.podeConsultarEstados()) {
            collectionModel.add(algaLinks.linkToEstados());
        }

        return collectionModel;
    }

    public Estado toDomainObject(EstadoInput estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }

    public void copyToDomainObject(EstadoInput estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }

}
