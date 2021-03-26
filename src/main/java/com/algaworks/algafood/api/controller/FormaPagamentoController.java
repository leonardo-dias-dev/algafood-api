package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.converter.FormaPagamentoConverter;
import com.algaworks.algafood.api.dto.request.FormaPagamentoRequest;
import com.algaworks.algafood.api.dto.response.FormaPagamentoResponse;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private FormaPagamentoConverter formaPagamentoConverter;
	
	@GetMapping
	public List<FormaPagamentoResponse> listar() {
		List<FormaPagamento> formaPagamentos = formaPagamentoService.listar();

		return formaPagamentoConverter.toCollectionResponseDto(formaPagamentos);
	}
	
	@GetMapping("/{id}")
	public FormaPagamentoResponse buscar(@PathVariable Long id) {
		FormaPagamento formaPagamento = formaPagamentoService.buscar(id);

		return formaPagamentoConverter.toResponseDto(formaPagamento);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoResponse adicionar(@Valid @RequestBody FormaPagamentoRequest formaPagamentoRequest) {
		FormaPagamento formaPagamento = formaPagamentoConverter.toEntity(formaPagamentoRequest);
		
		formaPagamento = formaPagamentoService.salvar(formaPagamento);
		
		return formaPagamentoConverter.toResponseDto(formaPagamento);
	}
	
	@PutMapping("/{id}")
	public FormaPagamentoResponse atualizar(@PathVariable Long id, @Valid @RequestBody FormaPagamentoRequest formaPagamentoRequest) {
		FormaPagamento formaPagamento = formaPagamentoService.buscar(id);
		
		formaPagamentoConverter.copyToEntity(formaPagamentoRequest, formaPagamento);
		
		formaPagamento = formaPagamentoService.salvar(formaPagamento);
		
		return formaPagamentoConverter.toResponseDto(formaPagamento);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		formaPagamentoService.remover(id);
	}
	
}
