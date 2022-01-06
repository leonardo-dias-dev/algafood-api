package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.converter.CozinhaConverter;
import com.algaworks.algafood.api.v1.dto.input.CozinhaInput;
import com.algaworks.algafood.api.v1.dto.model.CozinhaModel;
import com.algaworks.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/v1/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaConverter cozinhaConverter;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @Override
    @GetMapping
    public PagedModel<CozinhaModel> listar(@PageableDefault(page = 10) Pageable pageable) {
        Page<Cozinha> cozinhaPage = cozinhaService.listarPaginado(pageable);

        return pagedResourcesAssembler.toModel(cozinhaPage, cozinhaConverter);
    }

    @Override
    @GetMapping("/{id}")
    public CozinhaModel buscar(@PathVariable Long id) {
        Cozinha cozinha = cozinhaService.buscar(id);

        return cozinhaConverter.toModel(cozinha);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel adicionar(@Valid @RequestBody CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaConverter.toDomainObject(cozinhaInput);

        cozinha = cozinhaService.salvar(cozinha);

        return cozinhaConverter.toModel(cozinha);
    }

    @Override
    @PutMapping("/{id}")
    public CozinhaModel atualizar(@PathVariable Long id, @Valid @RequestBody CozinhaInput cozinhaInput) {
        Cozinha cozinhaBanco = cozinhaService.buscar(id);

        cozinhaConverter.copyToDomainObject(cozinhaInput, cozinhaBanco);

        cozinhaBanco = cozinhaService.salvar(cozinhaBanco);

        return cozinhaConverter.toModel(cozinhaBanco);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cozinhaService.remover(id);
    }

}
