package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v1.converter.CidadeConverter;
import com.algaworks.algafood.api.v1.dto.input.CidadeInput;
import com.algaworks.algafood.api.v1.dto.model.CidadeModel;
import com.algaworks.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.core.security.resourceserver.CheckSecurity;
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
@RequestMapping(path = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeConverter cidadeConverter;

    @Override
    @GetMapping
    @CheckSecurity.Cidades.Consultar
    public CollectionModel<CidadeModel> listar() {
        List<Cidade> cidades = cidadeService.listar();

        return cidadeConverter.toCollectionModel(cidades);
    }

    @Override
    @GetMapping(path = "/{id}")
    @CheckSecurity.Cidades.Consultar
    public CidadeModel buscar(@PathVariable Long id) {
        Cidade cidade = cidadeService.buscar(id);

        return cidadeConverter.toModel(cidade);
    }

    @Override
    @PostMapping
    @CheckSecurity.Cidades.Editar
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@Valid @RequestBody CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeConverter.toDomainObject(cidadeInput);

            cidade = cidadeService.salvar(cidade);

            CidadeModel cidadeModel = cidadeConverter.toModel(cidade);

            ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());

            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @CheckSecurity.Cidades.Editar
    @PutMapping(path = "/{id}")
    public CidadeModel atualizar(@PathVariable Long id, @Valid @RequestBody CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeService.buscar(id);

            cidadeConverter.copyToDomainObject(cidadeInput, cidade);

            cidade = cidadeService.salvar(cidade);

            return cidadeConverter.toModel(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @CheckSecurity.Cidades.Editar
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cidadeService.remover(id);
    }

}
