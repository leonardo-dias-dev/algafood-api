package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.converter.PermissaoConverter;
import com.algaworks.algafood.api.v1.dto.model.PermissaoModel;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.core.security.resourceserver.AlgaSecurity;
import com.algaworks.algafood.core.security.resourceserver.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoConverter permissaoConverter;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    @Override
    @GetMapping
    @CheckSecurity.UsuariosGruposPermissoes.Consultar
    public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscar(grupoId);

        CollectionModel<PermissaoModel> permissaoModels = permissaoConverter.toCollectionModel(grupo.getPermissoes())
                .removeLinks();

        permissaoModels.add(algaLinks.linkToGrupoPermissoes(grupoId));

        if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
            permissaoModels.add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

            permissaoModels.getContent().forEach(e -> {
                e.add(algaLinks.linkToGrupoPermissaoDesassociacao(grupoId, e.getId(), "desassociar"));
            });
        }

        return permissaoModels;
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "/{permissaoId}")
    @CheckSecurity.UsuariosGruposPermissoes.Editar
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{permissaoId}")
    @CheckSecurity.UsuariosGruposPermissoes.Editar
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }

}
