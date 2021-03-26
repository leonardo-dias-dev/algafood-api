package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.dto.request.EstadoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.dto.converter.EstadoConverter;
import com.algaworks.algafood.api.dto.response.EstadoResponse;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private EstadoConverter estadoConverter;

	@GetMapping
	public List<EstadoResponse> listar() {
		List<Estado> estados = estadoService.listar();
		
		return estadoConverter.toCollectionResponseDto(estados);
	}

	@GetMapping("/{id}")
	public EstadoResponse buscar(@PathVariable Long id) {
		Estado estado = estadoService.buscar(id);
		
		return estadoConverter.toResponseDto(estado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoResponse adicionar(@Valid @RequestBody EstadoRequest estadoRequest) {
		Estado estado = estadoConverter.toEntity(estadoRequest);
		
		estado = estadoService.salvar(estado);
		
		return estadoConverter.toResponseDto(estado);
	}

	@PutMapping("/{id}")
	public EstadoResponse atualizar(@PathVariable Long id, @Valid @RequestBody EstadoRequest estadoRequest) {
		Estado estadoBanco = estadoService.buscar(id);
		
		estadoConverter.copyToEntity(estadoRequest, estadoBanco);
		
		estadoBanco = estadoService.salvar(estadoBanco);
		
		return estadoConverter.toResponseDto(estadoBanco);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		estadoService.remover(id);
	}
	
}
