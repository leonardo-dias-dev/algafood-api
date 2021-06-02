package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.dto.request.CidadeRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeConverter cidadeConverter;

	@GetMapping
	@ApiOperation("Lista as cidades")
	public List<CidadeResponse> listar() {
		List<Cidade> cidades = cidadeService.listar();
		
		return cidadeConverter.toCollectionResponseDto(cidades);
	}

	@GetMapping("/{id}")
	@ApiOperation("Busca uma cidade por ID")
	public CidadeResponse buscar(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long id) {
		Cidade cidade = cidadeService.buscar(id);
		
		return cidadeConverter.toResponseDto(cidade);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation("Cadastra uma cidade")
	public CidadeResponse adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade") @Valid @RequestBody CidadeRequest cidadeRequest) {
		try {
			Cidade cidade = cidadeConverter.toEntity(cidadeRequest);
			
			cidade = cidadeService.salvar(cidade);
			
			return cidadeConverter.toResponseDto(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	@ApiOperation("Atualiza uma cidade por ID")
	public CidadeResponse atualizar(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long id,
									@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados") @Valid @RequestBody CidadeRequest cidadeRequest) {
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
	@ApiOperation("Exclui uma cidade por ID")
	public void remover(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long id) {
		cidadeService.remover(id);
	}
	
}
