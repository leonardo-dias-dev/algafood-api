package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import com.algaworks.algafood.api.v1.dto.input.RestauranteInput;
import com.algaworks.algafood.api.v1.dto.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.dto.model.RestauranteModel;
import com.algaworks.algafood.api.v1.dto.model.RestauranteResumoModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

    @ApiOperation(value = "Listar restaurantes")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
                    name = "projecao", paramType = "query", type = "string")
    })
    CollectionModel<RestauranteResumoModel> listar();

    @ApiIgnore
    @ApiOperation(value = "Listar restaurantes", hidden = true)
    CollectionModel<RestauranteApenasNomeModel> listarApenasNome();

    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestauranteModel buscar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Cadastra um restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Restaurante cadastrado")
    })
    RestauranteModel adicionar(@ApiParam(name = "corpo", value = "Representaçào de um novo restaurante", required = true) RestauranteInput restauranteInput);

    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurante atualizado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestauranteModel atualizar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id,
                               @ApiParam(name = "corpo", value = "Representação de um restaurante com novos dados", required = true) RestauranteInput restauranteInput);

    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
    })
    ResponseEntity<Void> ativar(@ApiParam(name = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
    })
    ResponseEntity<Void> inativar(@ApiParam(name = "ID de um restaurante", example = "1", required = true) Long id);

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
    ResponseEntity<Void> abrir(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
    })
    ResponseEntity<Void> fechar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);
}
