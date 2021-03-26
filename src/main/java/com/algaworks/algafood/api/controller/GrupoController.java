package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.dto.request.GrupoRequest;
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

import com.algaworks.algafood.api.dto.converter.GrupoConverter;
import com.algaworks.algafood.api.dto.response.GrupoResponse;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private GrupoConverter grupoConverter;
	
	@GetMapping
	public List<GrupoResponse> listar() {
		List<Grupo> grupos = grupoService.listar();
		
		return grupoConverter.toCollectionResponseDto(grupos);
	}
	
	@GetMapping("/{id}")
	public GrupoResponse buscar(@PathVariable Long id) {
		Grupo grupo = grupoService.buscar(id);
		
		return grupoConverter.toResponseDto(grupo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoResponse adicionar(@Valid @RequestBody GrupoRequest grupoRequest) {
		Grupo grupo = grupoConverter.toEntity(grupoRequest);
		
		grupo = grupoService.salvar(grupo);
		
		return grupoConverter.toResponseDto(grupo);
	}
	
	@PutMapping("/{id}")
	public GrupoResponse atualizar(@PathVariable Long id, @Valid @RequestBody GrupoRequest grupoRequest) {
		Grupo grupo = grupoService.buscar(id);
		
		grupoConverter.copyToEntity(grupoRequest, grupo);
		
		grupo = grupoService.salvar(grupo);
		
		return grupoConverter.toResponseDto(grupo);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		grupoService.remover(id);
	}
	
}
