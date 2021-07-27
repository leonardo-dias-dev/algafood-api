package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.request.PedidoRequest;
import com.algaworks.algafood.api.dto.response.PedidoResponse;
import com.algaworks.algafood.api.dto.response.PedidoResumoResponse;
import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiOperation("Lista os pedidos")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campos", paramType = "query", type = "string", value = "Nomes das propriedades para filtrar na resposta, separados por vírgula")
    })
    Page<PedidoResumoResponse> pesquisar(PedidoFilter pedidoFilter, Pageable pageable);

    @ApiOperation("Busca um pedido por código")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campos", paramType = "query", type = "string", value = "Nomes das propriedades para filtrar na resposta, separados por vírgula")
    })
    PedidoResponse buscar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigo);

    @ApiOperation("Cadastra um pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido cadastrado")
    })
    PedidoResponse adicionar(@ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true) PedidoRequest pedidoRequest);

}
