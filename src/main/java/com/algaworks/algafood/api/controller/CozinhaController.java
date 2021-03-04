package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.algaworks.algafood.api.dto.converter.CozinhaConverter;
import com.algaworks.algafood.api.dto.request.CozinhaRequestDto;
import com.algaworks.algafood.api.dto.response.CozinhaResponseDto;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CozinhaConverter cozinhaConverter;

	@GetMapping
	public Page<CozinhaResponseDto> listar(@PageableDefault(page = 10) Pageable pageable) {
		Page<Cozinha> cozinhaPage = cozinhaService.listarPaginado(pageable);
		
		List<CozinhaResponseDto> cozinhas = cozinhaConverter.toCollectionResponseDto(cozinhaPage.getContent());
		
		Page<CozinhaResponseDto> cozinhaResponsePage = new PageImpl<>(cozinhas, pageable, cozinhaPage.getTotalElements());
		
		return cozinhaResponsePage;
	}

	@GetMapping("/{id}")
	public CozinhaResponseDto buscar(@PathVariable Long id) {
		Cozinha cozinha = cozinhaService.buscar(id);
		
		return cozinhaConverter.toResponseDto(cozinha);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaResponseDto adicionar(@Valid @RequestBody CozinhaRequestDto cozinhaRequestDto) {
		Cozinha cozinha = cozinhaConverter.toEntity(cozinhaRequestDto);
		
		cozinha = cozinhaService.salvar(cozinha);
		
		return cozinhaConverter.toResponseDto(cozinha);
	}

	@PutMapping("/{id}")
	public CozinhaResponseDto atualizar(@PathVariable Long id, @Valid @RequestBody CozinhaRequestDto cozinhaRequestDto) {
		Cozinha cozinhaBanco = cozinhaService.buscar(id);
		
		cozinhaConverter.copyToEntity(cozinhaRequestDto, cozinhaBanco);
		
		cozinhaBanco = cozinhaService.salvar(cozinhaBanco);
		
		return cozinhaConverter.toResponseDto(cozinhaBanco);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cozinhaService.remover(id);
	}

}
