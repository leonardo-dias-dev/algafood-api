package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.api.dto.request.GrupoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private GrupoConverter grupoConverter;
	
	@Override
	@GetMapping
	public List<GrupoResponse> listar() {
		List<Grupo> grupos = grupoService.listar();
		
		return grupoConverter.toCollectionResponseDto(grupos);
	}
	
	@Override
	@GetMapping("/{id}")
	public GrupoResponse buscar(@PathVariable Long id) {
		Grupo grupo = grupoService.buscar(id);
		
		return grupoConverter.toResponseDto(grupo);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoResponse adicionar(@Valid @RequestBody GrupoRequest grupoRequest) {
		Grupo grupo = grupoConverter.toEntity(grupoRequest);
		
		grupo = grupoService.salvar(grupo);
		
		return grupoConverter.toResponseDto(grupo);
	}
	
	@Override
	@PutMapping("/{id}")
	public GrupoResponse atualizar(@PathVariable Long id, @Valid @RequestBody GrupoRequest grupoRequest) {
		Grupo grupo = grupoService.buscar(id);
		
		grupoConverter.copyToEntity(grupoRequest, grupo);
		
		grupo = grupoService.salvar(grupo);
		
		return grupoConverter.toResponseDto(grupo);
	}
	
	@Override
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		grupoService.remover(id);
	}
	
}
