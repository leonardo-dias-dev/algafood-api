package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.converter.PermissaoConverter;
import com.algaworks.algafood.api.v1.dto.model.PermissaoModel;
import com.algaworks.algafood.api.v1.openapi.controller.PermissaoControllerOpenApi;
import com.algaworks.algafood.core.security.resourceserver.CheckSecurity;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private PermissaoConverter permissaoConverter;

    @Override
    @GetMapping
    @CheckSecurity.UsuariosGruposPermissoes.Consultar
    public CollectionModel<PermissaoModel> listar() {
        List<Permissao> todasPermissoes = permissaoRepository.findAll();

        return permissaoConverter.toCollectionModel(todasPermissoes);
    }
}
