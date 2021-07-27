package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

    @ApiOperation("Confirmação do pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido confirmado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    void confirmar(@ApiParam(value = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigo);


    @ApiOperation("Registrar entrega do pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Entrega do pedido realizado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    void entregar(@ApiParam(value = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigo);

    @ApiOperation("Cancelamento do pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido cancelado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    void cancelar(@ApiParam(value = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigo);

}
