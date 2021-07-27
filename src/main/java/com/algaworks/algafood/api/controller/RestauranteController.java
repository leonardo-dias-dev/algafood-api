package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.converter.RestauranteConverter;
import com.algaworks.algafood.api.dto.request.RestauranteRequest;
import com.algaworks.algafood.api.dto.response.RestauranteResponse;
import com.algaworks.algafood.api.dto.view.RestauranteView;
import com.algaworks.algafood.api.openapi.controller.RestauranteControllerOpanApi;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpanApi {

	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private RestauranteConverter restauranteConverter;

	@Override
	@GetMapping
	@JsonView(RestauranteView.Resumo.class)
	public List<RestauranteResponse> listar() {
		List<Restaurante> restaurantes = restauranteService.listar();
		
		return restauranteConverter.toCollectionResponseDto(restaurantes);
	}

	@Override
	@GetMapping(params = "projecao=apenas-nome")
	@JsonView(RestauranteView.ApenasNome.class)
	public List<RestauranteResponse> listarApenasNome() {
		return listar();
	}

	@Override
	@GetMapping("/{id}")
	public RestauranteResponse buscar(@PathVariable Long id) {
		Restaurante restaurante = restauranteService.buscar(id);
		
		return restauranteConverter.toResponseDto(restaurante);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteResponse adicionar(@Valid @RequestBody RestauranteRequest restauranteRequest) {
		try {
			Restaurante restaurante = restauranteConverter.toEntity(restauranteRequest);
			
			restaurante = restauranteService.salvar(restaurante);
			
			return restauranteConverter.toResponseDto(restaurante);
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	@PutMapping("/{id}")
	public RestauranteResponse atualizar(@PathVariable Long id, @Valid @RequestBody RestauranteRequest restauranteRequest) {
		try {
			Restaurante restauranteBanco = restauranteService.buscar(id);
			
			restauranteConverter.copyToEntity(restauranteRequest, restauranteBanco);
			
			restauranteBanco = restauranteService.salvar(restauranteBanco);
			
			return restauranteConverter.toResponseDto(restauranteBanco);
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		restauranteService.ativar(id);
	}
	
	@Override
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		restauranteService.inativar(id);
	}
	
	@Override
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restaurantesIds) {
		try {
			restauranteService.ativar(restaurantesIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	@DeleteMapping("/inativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restaurantesIds) {
		try {
			restauranteService.inativar(restaurantesIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	@PutMapping("/{id}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long id) {
		restauranteService.abrir(id);
	}
	
	@Override
	@PutMapping("/{id}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long id) {
		restauranteService.fechar(id);
	}

}
