package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.dto.request.ProdutoRequest;
import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.dto.converter.ProdutoConverter;
import com.algaworks.algafood.api.dto.response.ProdutoResponse;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private ProdutoConverter produtoConverter;
	
	@Override
    @GetMapping
	public List<ProdutoResponse> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscar(restauranteId);
		
		List<Produto> produtos = produtoService.listar(restaurante);
		
		return produtoConverter.toCollectionResponseDto(produtos);
	}
	
	@Override
    @GetMapping("/{produtoId}")
	public ProdutoResponse buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Produto produto = produtoService.buscar(produtoId, restauranteId);
		
		return produtoConverter.toResponseDto(produto);
	}
	
	@Override
    @PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoResponse adicionar(@PathVariable Long restauranteId, @Valid @RequestBody ProdutoRequest produtoRequest) {
		Restaurante restaurante = restauranteService.buscar(restauranteId);
		
		Produto produto = produtoConverter.toEntity(produtoRequest);
		
		produto.setRestaurante(restaurante);
		
		produto = produtoService.salvar(produto);
		
		return produtoConverter.toResponseDto(produto);
	}
	
	@Override
    @PutMapping("/{produtoId}")
	public ProdutoResponse atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid @RequestBody ProdutoRequest produtoRequest) {
		Produto produto = produtoService.buscar(produtoId, restauranteId);
		
		produtoConverter.copyToEntity(produtoRequest, produto);
		
		produto = produtoService.salvar(produto);
		
		return produtoConverter.toResponseDto(produto);
	}
	
}
