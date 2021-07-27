package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.response.GrupoResponse;
import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

    @ApiOperation("Lista os grupos associados a um usuário")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    List<GrupoResponse> listar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);

    @ApiOperation("Associação de grupo com usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = Problem.class)
    })
    void associar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
                  @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId);

    @ApiOperation("Desssociação de grupo com usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desssociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = Problem.class)
    })
    void desassociar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
                     @ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId);

}
