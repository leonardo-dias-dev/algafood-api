package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import com.algaworks.algafood.api.v1.dto.input.PedidoInput;
import com.algaworks.algafood.api.v1.dto.model.PedidoModel;
import com.algaworks.algafood.api.v1.dto.model.PedidoResumoModel;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiOperation("Lista os pedidos")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campos", paramType = "query", type = "string", value = "Nomes das propriedades para filtrar na resposta, separados por vírgula")
    })
    PagedModel<PedidoResumoModel> pesquisar(PedidoFilter pedidoFilter, Pageable pageable);

    @ApiOperation("Busca um pedido por código")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campos", paramType = "query", type = "string", value = "Nomes das propriedades para filtrar na resposta, separados por vírgula")
    })
    PedidoModel buscar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigo);

    @ApiOperation("Cadastra um pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido cadastrado")
    })
    PedidoModel adicionar(@ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true) PedidoInput pedidoInput);

}
