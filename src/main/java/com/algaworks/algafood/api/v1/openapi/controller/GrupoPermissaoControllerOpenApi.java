package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import com.algaworks.algafood.api.v1.dto.model.PermissaoModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

    @ApiOperation("Lista as permissões associadas a um grupo")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    CollectionModel<PermissaoModel> listar(@ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId);

    @ApiOperation("Associação de permissão com grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "grupo ou permissão não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> associar(@ApiParam(value = "DI do grupo", example = "1", required = true) Long grupoId,
                                  @ApiParam(value = "ID da permissão", example = "1", required = true) Long permissaoId);

    @ApiOperation("Associação de permissão com grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "grupo ou permissão não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> desassociar(@ApiParam(value = "DI do grupo", example = "1", required = true) Long grupoId,
                                     @ApiParam(value = "ID da permissão", example = "1", required = true) Long permissaoId);

}
