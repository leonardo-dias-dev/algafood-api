package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.converter.UsuarioConverter;
import com.algaworks.algafood.api.v1.dto.input.SenhaInput;
import com.algaworks.algafood.api.v1.dto.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.dto.input.UsuarioInput;
import com.algaworks.algafood.api.v1.dto.model.UsuarioModel;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.core.security.resourceserver.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioConverter usuarioConverter;

    @Override
    @GetMapping
    @CheckSecurity.UsuariosGruposPermissoes.Consultar
    public CollectionModel<UsuarioModel> listar() {
        List<Usuario> usuarios = usuarioService.listar();

        return usuarioConverter.toCollectionModel(usuarios);
    }

    @Override
    @GetMapping(path = "/{id}")
    @CheckSecurity.UsuariosGruposPermissoes.Consultar
    public UsuarioModel buscar(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscar(id);

        return usuarioConverter.toModel(usuario);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CheckSecurity.UsuariosGruposPermissoes.Editar
    public UsuarioModel adicionar(@Valid @RequestBody UsuarioComSenhaInput usuarioComSenhaInput) {
        Usuario usuario = usuarioConverter.toDomainObject(usuarioComSenhaInput);

        usuario = usuarioService.salvar(usuario);

        return usuarioConverter.toModel(usuario);
    }

    @Override
    @PutMapping(path = "/{id}")
    @CheckSecurity.UsuariosGruposPermissoes.AlterarUsuario
    public UsuarioModel atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioInput usuarioInput) {
        Usuario usuario = usuarioService.buscar(id);

        usuarioConverter.copyToDomainObject(usuarioInput, usuario);

        usuario = usuarioService.salvar(usuario);

        return usuarioConverter.toModel(usuario);
    }

    @Override
    @PutMapping(path = "/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckSecurity.UsuariosGruposPermissoes.Editar
    public void alterarSenha(@PathVariable Long id, @Valid @RequestBody SenhaInput senhaInput) {
        usuarioService.alterarSenha(id, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }

}
