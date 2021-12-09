package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v2.converter.CidadeConverterV2;
import com.algaworks.algafood.api.v2.dto.input.CidadeInputV2;
import com.algaworks.algafood.api.v2.dto.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.openapi.controller.CidadeControllerV2OpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeControllerV2OpenApi {

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeConverterV2 cidadeConverterV2;

    @GetMapping
    public CollectionModel<CidadeModelV2> listar() {
        List<Cidade> cidades = cidadeService.listar();

        return cidadeConverterV2.toCollectionModel(cidades);
    }

    @Override
    @GetMapping("/{id}")
    public CidadeModelV2 buscar(@PathVariable Long id) {
        Cidade cidade = cidadeService.buscar(id);

        return cidadeConverterV2.toModel(cidade);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModelV2 adicionar(@Valid @RequestBody CidadeInputV2 cidadeInput) {
        try {
            Cidade cidade = cidadeConverterV2.toDomainObject(cidadeInput);

            cidade = cidadeService.salvar(cidade);

            CidadeModelV2 cidadeModel = cidadeConverterV2.toModel(cidade);

            ResourceUriHelper.addUriInResponseHeader(cidadeModel.getIdCidade());

            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @PutMapping("/{id}")
    public CidadeModelV2 atualizar(@PathVariable Long id, @Valid @RequestBody CidadeInputV2 cidadeInput) {
        try {
            Cidade cidade = cidadeService.buscar(id);

            cidadeConverterV2.copyToDomainObject(cidadeInput, cidade);

            cidade = cidadeService.salvar(cidade);

            return cidadeConverterV2.toModel(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cidadeService.remover(id);
    }

}
