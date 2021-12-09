package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.converter.UsuarioConverter;
import com.algaworks.algafood.api.v1.dto.model.UsuarioModel;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private UsuarioConverter usuarioConverter;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        CollectionModel<UsuarioModel> collectionModel = usuarioConverter.toCollectionModel(restaurante.getResponsaveis())
                .removeLinks()
                .add(algaLinks.linkToResponsaveisRestaurante(restauranteId))
                .add(algaLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));

        collectionModel.getContent().forEach(e -> {
            e.add(algaLinks.linkToRestauranteResponsavelDesassociacao(restauranteId, e.getId(), "desassociar"));
        });

        return collectionModel;
    }

    @Override
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.associarResponsavel(restauranteId, usuarioId);

        return ResponseEntity.noContent()
                .build();
    }

    @Override
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.desassociarResponsavel(restauranteId, usuarioId);

        return ResponseEntity.noContent()
                .build();
    }

}
