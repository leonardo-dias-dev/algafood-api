package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.api.dto.converter.CidadeConverter;
import com.algaworks.algafood.api.dto.request.CidadeRequest;
import com.algaworks.algafood.api.dto.response.CidadeResponse;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeConverter cidadeConverter;

	@Override
	@GetMapping
	public List<CidadeResponse> listar() {
		List<Cidade> cidades = cidadeService.listar();
		
		return cidadeConverter.toCollectionResponseDto(cidades);
	}

	@Override
	@GetMapping("/{id}")
	public CidadeResponse buscar(@PathVariable Long id) {
		Cidade cidade = cidadeService.buscar(id);
		
		return cidadeConverter.toResponseDto(cidade);
	}

	@Override
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

	@Override
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

	@Override
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cidadeService.remover(id);
	}
	
}
