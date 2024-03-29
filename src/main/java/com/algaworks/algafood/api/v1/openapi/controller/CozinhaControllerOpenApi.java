package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import com.algaworks.algafood.api.v1.dto.input.CozinhaInput;
import com.algaworks.algafood.api.v1.dto.model.CozinhaModel;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas")
    PagedModel<CozinhaModel> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaModel buscar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long id);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha cadastrada")
    })
    CozinhaModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true) CozinhaInput cozinhaInput);

    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaModel atualizar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long id,
                           @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados", required = true) CozinhaInput cozinhaInput);

    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluída"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long id);
}
