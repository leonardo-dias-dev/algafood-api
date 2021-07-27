package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.request.ProdutoRequest;
import com.algaworks.algafood.api.dto.response.ProdutoResponse;
import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

    @ApiOperation("Lista todos os produtos associados ao restaurante")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    List<ProdutoResponse> listar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);

    @ApiOperation("Busca um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)
    })
    ProdutoResponse buscar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                           @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId);

    @ApiOperation("Cadastra um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto cadastrado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ProdutoResponse adicionar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                              @ApiParam(name = "corpo", value = "Representação de um novo produto", required = true) ProdutoRequest produtoRequest);

    @ApiOperation("Atualiza um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto atualizado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ProdutoResponse atualizar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                              @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId,
                              @ApiParam(name = "corpo", value = "Representação de um novo produto com novos dados", required = true) ProdutoRequest produtoRequest);

}
