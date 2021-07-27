package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.request.GrupoRequest;
import com.algaworks.algafood.api.dto.response.GrupoResponse;
import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation("Lista os grupos")
    List<GrupoResponse> listar();

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoResponse buscar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long id);

    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Grupo cadastrado"),
    })
    GrupoResponse adicionar(@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true) GrupoRequest grupoRequest);

    @ApiOperation("Atualizada um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo atualizado"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoResponse atualizar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long id,
                            @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados", required = true) GrupoRequest grupoRequest);

    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo excluído"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long id);

}
