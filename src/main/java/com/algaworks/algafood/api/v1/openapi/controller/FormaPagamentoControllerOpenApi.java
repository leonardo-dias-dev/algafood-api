package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import com.algaworks.algafood.api.v1.dto.input.FormaPagamentoInput;
import com.algaworks.algafood.api.v1.dto.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

    @ApiOperation(value = "Lista as formas de pagamento", response = FormasPagamentoModelOpenApi.class)
    ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request);

    @ApiOperation("Busca uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrado", response = Problem.class)
    })
    ResponseEntity<FormaPagamentoModel> buscar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long id, ServletWebRequest servletWebRequest);

    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento cadastrada")
    })
    FormaPagamentoModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento", required = true) FormaPagamentoInput formaPagamentoInput);

    @ApiOperation("Atualiza uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrado", response = Problem.class)
    })
    FormaPagamentoModel atualizar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long id,
                                  @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com novos dados", required = true) FormaPagamentoInput formaPagamentoInput);

    @ApiOperation("Excluí uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrado", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long id);

}
