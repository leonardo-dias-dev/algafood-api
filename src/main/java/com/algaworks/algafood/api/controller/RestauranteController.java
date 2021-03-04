package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.dto.converter.RestauranteConverter;
import com.algaworks.algafood.api.dto.request.RestauranteRequestDto;
import com.algaworks.algafood.api.dto.response.RestauranteResponseDto;
import com.algaworks.algafood.api.dto.view.RestauranteView;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private SmartValidator smartValidator;
	
	@Autowired
	private RestauranteConverter restauranteConverter;
	
	@GetMapping
	@JsonView(RestauranteView.Resumo.class)
	public List<RestauranteResponseDto> listar() {
		List<Restaurante> restaurantes = restauranteService.listar();
		
		return restauranteConverter.toCollectionResponseDto(restaurantes);
	}
	
	@GetMapping(params = "projecao=apenas-nome")
	@JsonView(RestauranteView.ApenasNome.class)
	public List<RestauranteResponseDto> listarApenasNome() {
		return listar();
	}

	@GetMapping("/{id}")
	public RestauranteResponseDto buscar(@PathVariable Long id) {
		Restaurante restaurante = restauranteService.buscar(id);
		
		return restauranteConverter.toResponseDto(restaurante);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteResponseDto adicionar(@Valid @RequestBody RestauranteRequestDto restauranteRequestDto) {
		try {
			Restaurante restaurante = restauranteConverter.toEntity(restauranteRequestDto);
			
			restaurante = restauranteService.salvar(restaurante);
			
			return restauranteConverter.toResponseDto(restaurante);
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public RestauranteResponseDto atualizar(@PathVariable Long id, @Valid @RequestBody RestauranteRequestDto restauranteRequestDto) {
		try {
			Restaurante restauranteBanco = restauranteService.buscar(id);
			
			restauranteConverter.copyToEntity(restauranteRequestDto, restauranteBanco);
			
			restauranteBanco = restauranteService.salvar(restauranteBanco);
			
			return restauranteConverter.toResponseDto(restauranteBanco);
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		restauranteService.ativar(id);
	}
	
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		restauranteService.inativar(id);
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restaurantesIds) {
		try {
			restauranteService.ativar(restaurantesIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/inativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restaurantesIds) {
		try {
			restauranteService.inativar(restaurantesIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long id) {
		restauranteService.abrir(id);
	}
	
	@PutMapping("/{id}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long id) {
		restauranteService.fechar(id);
	}
	
	@PatchMapping("/{id}")
	public Restaurante atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
		Restaurante restauranteBanco = restauranteService.buscar(id);

		merge(campos, restauranteBanco, request);
		
		validate(restauranteBanco, "restaurante");

		//return restauranteService.atualizar(id, restauranteBanco);
		return null;
	}

	private void validate(Restaurante restaurante, String objectName) {
		BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		
		smartValidator.validate(restaurante, beanPropertyBindingResult);
		
		if (beanPropertyBindingResult.hasErrors()) {
			throw new ValidacaoException(beanPropertyBindingResult);
		}
	}

	private void merge(Map<String, Object> campos, Restaurante restaurante, HttpServletRequest request) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

			Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);

			campos.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);

				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

				ReflectionUtils.setField(field, restaurante, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}

}
