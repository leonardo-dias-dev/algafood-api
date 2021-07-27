package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.request.CozinhaRequest;
import com.algaworks.algafood.api.dto.response.CozinhaResponse;
import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas")
    Page<CozinhaResponse> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaResponse buscar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long id);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha cadastrada")
    })
    CozinhaResponse adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true) CozinhaRequest cozinhaRequest);

    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaResponse atualizar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long id,
                              @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados", required = true) CozinhaRequest cozinhaRequest);

    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluída"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long id);
}
