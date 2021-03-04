package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.dto.converter.FormaPagamentoConverter;
import com.algaworks.algafood.api.dto.request.FormaPagamentoRequestDto;
import com.algaworks.algafood.api.dto.response.FormaPagamentoResponseDto;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private FormaPagamentoConverter formaPagamentoConverter;
	
	@GetMapping
	public List<FormaPagamentoResponseDto> listar() {
		List<FormaPagamento> formaPagamentos = formaPagamentoService.listar();
		
		return formaPagamentoConverter.toCollectionResponseDto(formaPagamentos);
	}
	
	@GetMapping("/{id}")
	public FormaPagamentoResponseDto buscar(@PathVariable Long id) {
		FormaPagamento formaPagamento = formaPagamentoService.buscar(id);
		
		return formaPagamentoConverter.toResponseDto(formaPagamento);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoResponseDto adicionar(@Valid @RequestBody FormaPagamentoRequestDto formaPagamentoRequestDto) {
		FormaPagamento formaPagamento = formaPagamentoConverter.toEntity(formaPagamentoRequestDto);
		
		formaPagamento = formaPagamentoService.salvar(formaPagamento);
		
		return formaPagamentoConverter.toResponseDto(formaPagamento);
	}
	
	@PutMapping("/{id}")
	public FormaPagamentoResponseDto atualizar(@PathVariable Long id, @Valid @RequestBody FormaPagamentoRequestDto formaPagamentoRequestDto) {
		FormaPagamento formaPagamento = formaPagamentoService.buscar(id);
		
		formaPagamentoConverter.copyToEntity(formaPagamentoRequestDto, formaPagamento);
		
		formaPagamento = formaPagamentoService.salvar(formaPagamento);
		
		return formaPagamentoConverter.toResponseDto(formaPagamento);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		formaPagamentoService.remover(id);
	}
	
}
