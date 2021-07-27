package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.request.RestauranteRequest;
import com.algaworks.algafood.api.dto.response.RestauranteResponse;
import com.algaworks.algafood.api.dto.view.RestauranteView;
import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import com.algaworks.algafood.api.openapi.model.RestauranteBasicoResponseOpenApi;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpanApi {

    @ApiOperation(value = "Listar restaurantes", response = RestauranteBasicoResponseOpenApi.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
                    name = "projecao", paramType = "query", type = "string")
    })
    List<RestauranteResponse> listar();

    @ApiOperation(value = "Listar restaurantes", hidden = true)
    List<RestauranteResponse> listarApenasNome();

    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestauranteResponse buscar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Cadastra um restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Restaurante cadastrado")
    })
    RestauranteResponse adicionar(@ApiParam(name = "corpo", value = "Representaçào de um novo restaurante", required = true) RestauranteRequest restauranteRequest);

    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurante atualizado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestauranteResponse atualizar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id,
                                  @ApiParam(name = "corpo", value = "Representação de um restaurante com novos dados", required = true) RestauranteRequest restauranteRequest);

    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
    })
    void ativar(@ApiParam(name = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
    })
    void inativar(@ApiParam(name = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante ativados com sucesso")
    })
    void ativarMultiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restaurantesIds);

    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante inativados com sucesso")
    })
    void inativarMultiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restaurantesIds);

    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
    })
    void abrir(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
    })
    void fechar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);
}
