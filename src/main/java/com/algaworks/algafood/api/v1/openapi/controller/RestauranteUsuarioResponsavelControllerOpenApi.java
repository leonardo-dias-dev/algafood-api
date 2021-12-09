package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import com.algaworks.algafood.api.v1.dto.model.UsuarioModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

    @ApiOperation("Lista os usuários responsáveis associados ao restaurante")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    CollectionModel<UsuarioModel> listar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);

    @ApiOperation("Associação do restaurante com o usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> associar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                                  @ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);

    @ApiOperation("Desassociação do restaurante com o usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> desassociar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                                     @ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);

}
