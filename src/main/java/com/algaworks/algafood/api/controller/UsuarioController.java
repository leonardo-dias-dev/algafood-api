package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.dto.converter.UsuarioConverter;
import com.algaworks.algafood.api.dto.request.SenhaRequestDto;
import com.algaworks.algafood.api.dto.request.UsuarioComSenhaRequestDto;
import com.algaworks.algafood.api.dto.request.UsuarioSemSenhaRequestDto;
import com.algaworks.algafood.api.dto.response.UsuarioResponseDto;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioConverter usuarioConverter;
	
	@GetMapping
	public List<UsuarioResponseDto> listar() {
		List<Usuario> usuarios = usuarioService.listar();
		
		return usuarioConverter.toCollectionResponseDto(usuarios);
	}
	
	@GetMapping("/{id}")
	public UsuarioResponseDto buscar(@PathVariable Long id) {
		Usuario usuario = usuarioService.buscar(id);
		
		return usuarioConverter.toResponseDto(usuario);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioResponseDto adicionar(@Valid @RequestBody UsuarioComSenhaRequestDto usuarioComSenhaRequestDto) {
		Usuario usuario = usuarioConverter.toEntity(usuarioComSenhaRequestDto);
		
		usuario = usuarioService.salvar(usuario);
		
		return usuarioConverter.toResponseDto(usuario);
	}
	
	@PutMapping("/{id}")
	public UsuarioResponseDto atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioSemSenhaRequestDto usuarioSemSenhaRequestDto) {
		Usuario usuario = usuarioService.buscar(id);
		
		usuarioConverter.copyToEntity(usuarioSemSenhaRequestDto, usuario);
		
		usuario = usuarioService.salvar(usuario);
		
		return usuarioConverter.toResponseDto(usuario);
	}
	
	@PutMapping("/{id}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long id, @Valid @RequestBody SenhaRequestDto senhaRequestDto) {
		usuarioService.alterarSenha(id, senhaRequestDto.getSenhaAtual(), senhaRequestDto.getNovaSenha());
	}
	
}
