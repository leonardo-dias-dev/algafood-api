package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.converter.FormaPagamentoConverter;
import com.algaworks.algafood.api.v1.dto.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafood.core.security.resourceserver.AlgaSecurity;
import com.algaworks.algafood.core.security.resourceserver.CheckSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoConverter formaPagamentoConverter;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    @Override
    @GetMapping
    @CheckSecurity.Restaurantes.Consultar
    public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        CollectionModel<FormaPagamentoModel> formasPagamentoModel
                = formaPagamentoConverter.toCollectionModel(restaurante.getFormaPagamentos())
                .removeLinks();

        formasPagamentoModel.add(algaLinks.linkToRestauranteFormasPagamento(restauranteId));

        if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restauranteId)) {
            formasPagamentoModel.add(algaLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));

            formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
                formaPagamentoModel.add(algaLinks.linkToRestauranteFormaPagamentoDesassociacao(
                        restauranteId, formaPagamentoModel.getId(), "desassociar"));
            });
        }

        return formasPagamentoModel;
    }

    @Override
    @CheckSecurity.Restaurantes.Editar
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent()
                .build();
    }

    @Override
    @CheckSecurity.Restaurantes.Editar
    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent()
                .build();
    }

}
