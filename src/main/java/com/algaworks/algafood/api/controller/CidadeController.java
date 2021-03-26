package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.dto.request.CidadeRequest;
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

import com.algaworks.algafood.api.dto.converter.CidadeConverter;
import com.algaworks.algafood.api.dto.response.CidadeResponse;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeConverter cidadeConverter;

	@GetMapping
	public List<CidadeResponse> listar() {
		List<Cidade> cidades = cidadeService.listar();
		
		return cidadeConverter.toCollectionResponseDto(cidades);
	}

	@GetMapping("/{id}")
	public CidadeResponse buscar(@PathVariable Long id) {
		Cidade cidade = cidadeService.buscar(id);
		
		return cidadeConverter.toResponseDto(cidade);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeResponse adicionar(@Valid @RequestBody CidadeRequest cidadeRequest) {
		try {
			Cidade cidade = cidadeConverter.toEntity(cidadeRequest);
			
			cidade = cidadeService.salvar(cidade);
			
			return cidadeConverter.toResponseDto(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public CidadeResponse atualizar(@PathVariable Long id, @Valid @RequestBody CidadeRequest cidadeRequest) {
		try {
			Cidade cidadeBanco = cidadeService.buscar(id);
			
			cidadeConverter.copyToEntity(cidadeRequest, cidadeBanco);
			
			cidadeBanco = cidadeService.salvar(cidadeBanco);
			
			return cidadeConverter.toResponseDto(cidadeBanco);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cidadeService.remover(id);
	}
	
}
