package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.request.SenhaRequest;
import com.algaworks.algafood.api.dto.request.UsuarioComSenhaRequest;
import com.algaworks.algafood.api.dto.request.UsuarioSemSenhaRequest;
import com.algaworks.algafood.api.dto.response.UsuarioResponse;
import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

    @ApiOperation("Lista os usuários")
    List<UsuarioResponse> listar();

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioResponse buscar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long id);

    @ApiOperation("Cadastra um usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário cadastrado")
    })
    UsuarioResponse adicionar(@ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true) UsuarioComSenhaRequest usuarioComSenhaRequest);

    @ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário atualizado"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioResponse atualizar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long id,
                              @ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados", required = true) UsuarioSemSenhaRequest usuarioSemSenhaRequest);

    @ApiOperation("Atualiza a senha de um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Senha alterada com sucessso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    void alterarSenha(@ApiParam(value = "ID do usuário", example = "1", required = true) Long id,
                      @ApiParam(name = "corpo", value = "Representação de uma nova senha", required = true) SenhaRequest senhaRequest);

}
