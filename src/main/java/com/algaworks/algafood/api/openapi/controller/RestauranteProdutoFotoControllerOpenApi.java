package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.request.FotoProdutoRequest;
import com.algaworks.algafood.api.dto.response.FotoProdutoResponse;
import com.algaworks.algafood.api.exceptionhandler.detail.Problem;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Api(tags = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {

    @ApiOperation("Busca a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto do produto não encontrada", response = Problem.class)
    })
    FotoProdutoResponse buscar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                               @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId);

    @ApiOperation(value = "Busca a foto do produto em um restaurante", hidden = true)
    ResponseEntity<?> buscarArquivo(Long restauranteId, Long produtoId, String acceptHeader) throws HttpMediaTypeNotAcceptableException;

    @ApiOperation("Atualiza a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Foto do produto atualizada"),
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)
    })
    FotoProdutoResponse atualizarFoto(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                                      @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId,
                                      FotoProdutoRequest fotoProdutoResquestDto,
                                      @ApiParam(value = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)", required = true) MultipartFile multipartFile) throws IOException;

    @ApiOperation("Exclui a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Foto do produto excluída"),
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto do produto não encontrado", response = Problem.class)
    })
    void removerFoto(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                     @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId);

}
