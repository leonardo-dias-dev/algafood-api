package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.dto.model.RestauranteResumoModel;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteResumoModelConverter extends RepresentationModelAssemblerSupport<Restaurante, RestauranteResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteResumoModelConverter() {
        super(RestauranteController.class, RestauranteResumoModel.class);
    }

    @Override
    public RestauranteResumoModel toModel(Restaurante restaurante) {
        RestauranteResumoModel restauranteResumoModel = createModelWithId(restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteResumoModel);

        restauranteResumoModel.add(algaLinks.linkToRestaurantes("restaurantes"));
        restauranteResumoModel.getCozinha().add(algaLinks.linkToCozinha(restauranteResumoModel.getCozinha().getId()));

        return restauranteResumoModel;
    }

    @Override
    public CollectionModel<RestauranteResumoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurantes());
    }
}
