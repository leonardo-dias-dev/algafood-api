package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.request.FormaPagamentoRequest;
import com.algaworks.algafood.api.dto.response.FormaPagamentoResponse;
import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

    @ApiOperation("Lista das formas de pagamento")
    ResponseEntity<List<FormaPagamentoResponse>> listar(ServletWebRequest servletWebRequest);

    @ApiOperation("Busca uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrado", response = Problem.class)
    })
    ResponseEntity<FormaPagamentoResponse> buscar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long id, ServletWebRequest servletWebRequest);

    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento cadastrada")
    })
    FormaPagamentoResponse adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento", required = true) FormaPagamentoRequest formaPagamentoRequest);

    @ApiOperation("Atualiza uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrado", response = Problem.class)
    })
    FormaPagamentoResponse atualizar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long id,
                                     @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com novos dados", required = true) FormaPagamentoRequest formaPagamentoRequest);

    @ApiOperation("Excluí uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrado", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long id);

}
