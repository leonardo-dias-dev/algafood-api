package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.converter.ProdutoConverter;
import com.algaworks.algafood.api.v1.dto.input.ProdutoInput;
import com.algaworks.algafood.api.v1.dto.model.ProdutoModel;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoConverter produtoConverter;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping
    public CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteId,
                                                @RequestParam(required = false, defaultValue = "false") Boolean incluirInativos) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        List<Produto> produtos = produtoService.listar(restaurante, incluirInativos);

        return produtoConverter.toCollectionModel(produtos)
                .add(algaLinks.linkToProdutos(restauranteId));
    }

    @Override
    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = produtoService.buscar(produtoId, restauranteId);

        return produtoConverter.toModel(produto);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteId, @Valid @RequestBody ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        Produto produto = produtoConverter.toDomainObject(produtoInput);

        produto.setRestaurante(restaurante);

        produto = produtoService.salvar(produto);

        return produtoConverter.toModel(produto);
    }

    @Override
    @PutMapping("/{produtoId}")
    public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid @RequestBody ProdutoInput produtoInput) {
        Produto produto = produtoService.buscar(produtoId, restauranteId);

        produtoConverter.copyToDomainObject(produtoInput, produto);

        produto = produtoService.salvar(produto);

        return produtoConverter.toModel(produto);
    }

}
