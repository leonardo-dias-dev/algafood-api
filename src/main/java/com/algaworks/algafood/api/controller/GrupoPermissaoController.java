package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.dto.converter.PermissaoConverter;
import com.algaworks.algafood.api.dto.response.PermissaoResponse;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;

@RestController
@RequestMapping(path = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private PermissaoConverter permissaoConverter;
	
	@Override
	@GetMapping
	public List<PermissaoResponse> listar(@PathVariable Long grupoId) {
		Grupo grupo = grupoService.buscar(grupoId);
		
		return permissaoConverter.toCollectionResponseDto(grupo.getPermissoes());
	}
	
	@Override
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.associarPermissao(grupoId, permissaoId);
	}
	
	@Override
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.desassociarPermissao(grupoId, permissaoId);
	}
	
}
