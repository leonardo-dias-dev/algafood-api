package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.FotoProdutoConverter;
import com.algaworks.algafood.api.dto.input.FotoProdutoInput;
import com.algaworks.algafood.api.dto.model.FotoProdutoModel;
import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.FotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.FotoStorageService.FotoRecuperada;
import com.algaworks.algafood.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

    @Autowired
    private FotoProdutoService fotoProdutoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FotoProdutoConverter fotoProdutoConverter;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Override
    @GetMapping
    public FotoProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoProduto = fotoProdutoService.buscar(produtoId, restauranteId);

        return fotoProdutoConverter.toModel(fotoProduto);
    }

    @Override
    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> buscarArquivo(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                           @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto fotoProduto = fotoProdutoService.buscar(produtoId, restauranteId);

            verificarCompatibilidadeMediaType(fotoProduto.getContentType(), acceptHeader);

            FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

            if (fotoRecuperada.temUrl()) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(fotoProduto.getContentType()))
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                          @Valid FotoProdutoInput fotoProdutoResquestDto,
                                          @RequestPart MultipartFile multipartFile) throws IOException {
        Produto produto = produtoService.buscar(produtoId, restauranteId);

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoProdutoResquestDto.getDescricao());
        fotoProduto.setContentType(fotoProdutoResquestDto.getMultipartFile().getContentType());
        fotoProduto.setTamanho(fotoProdutoResquestDto.getMultipartFile().getSize());
        fotoProduto.setNomeArquivo(fotoProdutoResquestDto.getMultipartFile().getOriginalFilename());

        fotoProduto = fotoProdutoService.salvar(fotoProduto, fotoProdutoResquestDto.getMultipartFile().getInputStream());

        return fotoProdutoConverter.toModel(fotoProduto);
    }

    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        fotoProdutoService.remover(produtoId, restauranteId);
    }

    private void verificarCompatibilidadeMediaType(String contentType, String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        MediaType mediaType = MediaType.parseMediaType(contentType);
        List<MediaType> mediaTypes = MediaType.parseMediaTypes(acceptHeader);

        boolean compativel = mediaTypes.stream().anyMatch(media -> media.isCompatibleWith(mediaType));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypes);
        }
    }

}
