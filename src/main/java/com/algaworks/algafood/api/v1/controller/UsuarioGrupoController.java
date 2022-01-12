package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.converter.GrupoConverter;
import com.algaworks.algafood.api.v1.dto.model.GrupoModel;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.core.security.resourceserver.AlgaSecurity;
import com.algaworks.algafood.core.security.resourceserver.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoConverter grupoConverter;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    @Override
    @GetMapping
    @CheckSecurity.UsuariosGruposPermissoes.Consultar
    public CollectionModel<GrupoModel> listar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscar(usuarioId);

        CollectionModel<GrupoModel> gruposModel = grupoConverter.toCollectionModel(usuario.getGrupos())
                .removeLinks();

        if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
            gruposModel.add(algaLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));

            gruposModel.getContent().forEach(grupoModel -> {
                grupoModel.add(algaLinks.linkToUsuarioGrupoDesassociacao(
                        usuarioId, grupoModel.getId(), "desassociar"));
            });
        }

        return gruposModel;
    }

    @Override
    @PutMapping(path = "/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckSecurity.UsuariosGruposPermissoes.Editar
    public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.associarGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping(path = "/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckSecurity.UsuariosGruposPermissoes.Consultar
    public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desassociarGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }

}
