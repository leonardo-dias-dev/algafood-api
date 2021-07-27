package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.dto.request.SenhaRequest;
import com.algaworks.algafood.api.dto.request.UsuarioComSenhaRequest;
import com.algaworks.algafood.api.openapi.controller.UsuarioControllerOpenApi;
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

import com.algaworks.algafood.api.dto.converter.UsuarioConverter;
import com.algaworks.algafood.api.dto.request.UsuarioSemSenhaRequest;
import com.algaworks.algafood.api.dto.response.UsuarioResponse;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;

@RestController
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioConverter usuarioConverter;
	
	@Override
	@GetMapping
	public List<UsuarioResponse> listar() {
		List<Usuario> usuarios = usuarioService.listar();
		
		return usuarioConverter.toCollectionResponseDto(usuarios);
	}
	
	@Override
	@GetMapping("/{id}")
	public UsuarioResponse buscar(@PathVariable Long id) {
		Usuario usuario = usuarioService.buscar(id);
		
		return usuarioConverter.toResponseDto(usuario);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioResponse adicionar(@Valid @RequestBody UsuarioComSenhaRequest usuarioComSenhaRequest) {
		Usuario usuario = usuarioConverter.toEntity(usuarioComSenhaRequest);
		
		usuario = usuarioService.salvar(usuario);
		
		return usuarioConverter.toResponseDto(usuario);
	}
	
	@Override
	@PutMapping("/{id}")
	public UsuarioResponse atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioSemSenhaRequest usuarioSemSenhaRequest) {
		Usuario usuario = usuarioService.buscar(id);
		
		usuarioConverter.copyToEntity(usuarioSemSenhaRequest, usuario);
		
		usuario = usuarioService.salvar(usuario);
		
		return usuarioConverter.toResponseDto(usuario);
	}
	
	@Override
	@PutMapping("/{id}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long id, @Valid @RequestBody SenhaRequest senhaRequest) {
		usuarioService.alterarSenha(id, senhaRequest.getSenhaAtual(), senhaRequest.getNovaSenha());
	}
	
}
