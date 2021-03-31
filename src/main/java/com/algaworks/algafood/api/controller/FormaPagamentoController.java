package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.dto.converter.FormaPagamentoConverter;
import com.algaworks.algafood.api.dto.request.FormaPagamentoRequest;
import com.algaworks.algafood.api.dto.response.FormaPagamentoResponse;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private FormaPagamentoConverter formaPagamentoConverter;
	
	@GetMapping
	public ResponseEntity<List<FormaPagamentoResponse>> listar(ServletWebRequest servletWebRequest) {
		String eTag = eTag(servletWebRequest);

		if (servletWebRequest.checkNotModified(eTag)) {
			return null;
		}

		List<FormaPagamento> formaPagamentos = formaPagamentoService.listar();

		List<FormaPagamentoResponse> formasPagamentosResponse = formaPagamentoConverter.toCollectionResponseDto(formaPagamentos);

		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(formasPagamentosResponse);
	}

	public String eTag(ServletWebRequest servletWebRequest) {
		ShallowEtagHeaderFilter.disableContentCaching(servletWebRequest.getRequest());
		String eTag = "0";
		Optional<OffsetDateTime> dataUltimaAtualizacao = formaPagamentoService.getDataUltimaAtualizacao();

		if (dataUltimaAtualizacao.isPresent()) {
			eTag = String.valueOf(dataUltimaAtualizacao.get().toEpochSecond());
		}

		return eTag;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<FormaPagamentoResponse> buscar(@PathVariable Long id, ServletWebRequest servletWebRequest) {
		String eTag = eTag(servletWebRequest);

		if (servletWebRequest.checkNotModified(eTag)) {
			return null;
		}

		FormaPagamento formaPagamento = formaPagamentoService.buscar(id);

		FormaPagamentoResponse formaPagamentoResponse = formaPagamentoConverter.toResponseDto(formaPagamento);

		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(formaPagamentoResponse);
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
