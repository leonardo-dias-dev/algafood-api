package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.v2.converter.CozinhaConverterV2;
import com.algaworks.algafood.api.v2.dto.input.CozinhaInputV2;
import com.algaworks.algafood.api.v2.dto.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.openapi.controller.CozinhaControllerV2OpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
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

@RestController
@RequestMapping(path = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 implements CozinhaControllerV2OpenApi {

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaConverterV2 cozinhaConverterV2;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @Override
    @GetMapping
    public PagedModel<CozinhaModelV2> listar(@PageableDefault(page = 10) Pageable pageable) {
        Page<Cozinha> cozinhaPage = cozinhaService.listarPaginado(pageable);

        return pagedResourcesAssembler.toModel(cozinhaPage, cozinhaConverterV2);
    }

    @Override
    @GetMapping("/{id}")
    public CozinhaModelV2 buscar(@PathVariable Long id) {
        Cozinha cozinha = cozinhaService.buscar(id);

        return cozinhaConverterV2.toModel(cozinha);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModelV2 adicionar(@Valid @RequestBody CozinhaInputV2 cozinhaInputV2) {
        Cozinha cozinha = cozinhaConverterV2.toDomainObject(cozinhaInputV2);

        cozinha = cozinhaService.salvar(cozinha);

        return cozinhaConverterV2.toModel(cozinha);
    }

    @Override
    @PutMapping("/{id}")
    public CozinhaModelV2 atualizar(@PathVariable Long id, @Valid @RequestBody CozinhaInputV2 cozinhaInputV2) {
        Cozinha cozinhaBanco = cozinhaService.buscar(id);

        cozinhaConverterV2.copyToDomainObject(cozinhaInputV2, cozinhaBanco);

        cozinhaBanco = cozinhaService.salvar(cozinhaBanco);

        return cozinhaConverterV2.toModel(cozinhaBanco);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cozinhaService.remover(id);
    }

}
