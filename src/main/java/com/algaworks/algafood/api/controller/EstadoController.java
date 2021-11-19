package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.EstadoConverter;
import com.algaworks.algafood.api.dto.input.EstadoInput;
import com.algaworks.algafood.api.dto.model.EstadoModel;
import com.algaworks.algafood.api.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoConverter estadoConverter;

    @Override
    @GetMapping
    public CollectionModel<EstadoModel> listar() {
        List<Estado> estados = estadoService.listar();

        return estadoConverter.toCollectionModel(estados);
    }

    @Override
    @GetMapping("/{id}")
    public EstadoModel buscar(@PathVariable Long id) {
        Estado estado = estadoService.buscar(id);

        return estadoConverter.toModel(estado);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar(@Valid @RequestBody EstadoInput estadoInput) {
        Estado estado = estadoConverter.toDomainObject(estadoInput);

        estado = estadoService.salvar(estado);

        return estadoConverter.toModel(estado);
    }

    @Override
    @PutMapping("/{id}")
    public EstadoModel atualizar(@PathVariable Long id, @Valid @RequestBody EstadoInput estadoInput) {
        Estado estadoBanco = estadoService.buscar(id);

        estadoConverter.copyToDomainObject(estadoInput, estadoBanco);

        estadoBanco = estadoService.salvar(estadoBanco);

        return estadoConverter.toModel(estadoBanco);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        estadoService.remover(id);
    }

}
