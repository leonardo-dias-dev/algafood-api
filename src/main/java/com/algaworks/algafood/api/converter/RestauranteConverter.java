package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.dto.input.RestauranteInput;
import com.algaworks.algafood.api.dto.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RestauranteConverter extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteConverter() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));

        restauranteModel.getCozinha().add(
                algaLinks.linkToCozinha(restaurante.getCozinha().getId())
        );

        if (Objects.nonNull(restauranteModel.getEndereco()) && Objects.nonNull(restauranteModel.getEndereco().getCidade())) {
            restauranteModel.getEndereco().getCidade().add(
                    algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId())
            );
        }

        restauranteModel.add(
                algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento")
        );
        restauranteModel.add(
                algaLinks.linkToResponsaveisRestaurante(restaurante.getId(), "responsaveis")
        );

        if (restaurante.ativacaoPermitida()) {
            restauranteModel.add(
                    algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar")
            );
        }

        if (restaurante.inativacaoPermitida()) {
            restauranteModel.add(
                    algaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar")
            );
        }

        if (restaurante.aberturaPermitida()) {
            restauranteModel.add(
                    algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir")
            );
        }

        if (restaurante.fechamentoPermitido()) {
            restauranteModel.add(
                    algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar")
            );
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurantes());
    }

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());

        if (restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(restauranteInput, restaurante);
    }

}
