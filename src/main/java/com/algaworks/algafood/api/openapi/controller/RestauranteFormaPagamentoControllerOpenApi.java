package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.response.FormaPagamentoResponse;
import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

    @ApiOperation("Lista as formas de pagamento")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    List<FormaPagamentoResponse> listar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);

    @ApiOperation("Desassosiação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "AssDesassosiaçãoosiação realizada com sucesso", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    void desassociar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                     @ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId);


    @ApiOperation("Associação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Assosiação realizada com sucesso", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    void associar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                  @ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId);

}
