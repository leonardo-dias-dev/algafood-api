package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.converter.RestauranteApenasNomeModelConverter;
import com.algaworks.algafood.api.v1.converter.RestauranteConverter;
import com.algaworks.algafood.api.v1.converter.RestauranteResumoModelConverter;
import com.algaworks.algafood.api.v1.dto.input.RestauranteInput;
import com.algaworks.algafood.api.v1.dto.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.dto.model.RestauranteModel;
import com.algaworks.algafood.api.v1.dto.model.RestauranteResumoModel;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.core.security.resourceserver.CheckSecurity;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteConverter restauranteConverter;

    @Autowired
    private RestauranteResumoModelConverter restauranteResumoModelConverter;

    @Autowired
    private RestauranteApenasNomeModelConverter restauranteApenasNomeModelConverter;

    @Override
    @GetMapping
    @CheckSecurity.Restaurantes.Consultar
    public CollectionModel<RestauranteResumoModel> listar() {
        List<Restaurante> restaurantes = restauranteService.listar();

        return restauranteResumoModelConverter.toCollectionModel(restaurantes);
    }

    @Override
    @CheckSecurity.Restaurantes.Consultar
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeModel> listarApenasNome() {
        List<Restaurante> restaurantes = restauranteService.listar();

        return restauranteApenasNomeModelConverter.toCollectionModel(restaurantes);
    }

    @Override
    @GetMapping("/{id}")
    @CheckSecurity.Restaurantes.Consultar
    public RestauranteModel buscar(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscar(id);

        return restauranteConverter.toModel(restaurante);
    }

    @Override
    @PostMapping
    @CheckSecurity.Restaurantes.Editar
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@Valid @RequestBody RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteConverter.toDomainObject(restauranteInput);

            restaurante = restauranteService.salvar(restaurante);

            return restauranteConverter.toModel(restaurante);
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @PutMapping("/{id}")
    @CheckSecurity.Restaurantes.Editar
    public RestauranteModel atualizar(@PathVariable Long id, @Valid @RequestBody RestauranteInput restauranteInput) {
        try {
            Restaurante restauranteBanco = restauranteService.buscar(id);

            restauranteConverter.copyToDomainObject(restauranteInput, restauranteBanco);

            restauranteBanco = restauranteService.salvar(restauranteBanco);

            return restauranteConverter.toModel(restauranteBanco);
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @PutMapping("/{id}/ativo")
    @CheckSecurity.Restaurantes.Editar
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        restauranteService.ativar(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{id}/ativo")
    @CheckSecurity.Restaurantes.Editar
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        restauranteService.inativar(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/ativacoes")
    @CheckSecurity.Restaurantes.Editar
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restaurantesIds) {
        try {
            restauranteService.ativar(restaurantesIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @DeleteMapping("/inativacoes")
    @CheckSecurity.Restaurantes.Editar
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restaurantesIds) {
        try {
            restauranteService.inativar(restaurantesIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @PutMapping("/{id}/abertura")
    @CheckSecurity.Restaurantes.Funcionamento
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable Long id) {
        restauranteService.abrir(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{id}/fechamento")
    @CheckSecurity.Restaurantes.Funcionamento
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable Long id) {
        restauranteService.fechar(id);

        return ResponseEntity.noContent().build();
    }

}
