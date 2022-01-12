package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.converter.GrupoConverter;
import com.algaworks.algafood.api.v1.dto.input.GrupoInput;
import com.algaworks.algafood.api.v1.dto.model.GrupoModel;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.core.security.resourceserver.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoConverter grupoConverter;

    @Override
    @GetMapping
    @CheckSecurity.UsuariosGruposPermissoes.Consultar
    public CollectionModel<GrupoModel> listar() {
        List<Grupo> grupos = grupoService.listar();

        return grupoConverter.toCollectionModel(grupos);
    }

    @Override
    @GetMapping(path = "/{id}")
    @CheckSecurity.UsuariosGruposPermissoes.Consultar
    public GrupoModel buscar(@PathVariable Long id) {
        Grupo grupo = grupoService.buscar(id);

        return grupoConverter.toModel(grupo);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CheckSecurity.UsuariosGruposPermissoes.Editar
    public GrupoModel adicionar(@Valid @RequestBody GrupoInput grupoInput) {
        Grupo grupo = grupoConverter.toDomainObject(grupoInput);

        grupo = grupoService.salvar(grupo);

        return grupoConverter.toModel(grupo);
    }

    @Override
    @PutMapping(path = "/{id}")
    @CheckSecurity.UsuariosGruposPermissoes.Editar
    public GrupoModel atualizar(@PathVariable Long id, @Valid @RequestBody GrupoInput grupoInput) {
        Grupo grupo = grupoService.buscar(id);

        grupoConverter.copyToDomainObject(grupoInput, grupo);

        grupo = grupoService.salvar(grupo);

        return grupoConverter.toModel(grupo);
    }

    @Override
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckSecurity.UsuariosGruposPermissoes.Editar
    public void remover(@PathVariable Long id) {
        grupoService.remover(id);
    }

}
