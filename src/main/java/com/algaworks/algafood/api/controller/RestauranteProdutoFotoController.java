package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.dto.converter.FotoProdutoConverter;
import com.algaworks.algafood.api.dto.request.FotoProdutoRequest;
import com.algaworks.algafood.api.dto.response.FotoProdutoResponse;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.FotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.FotoStorageService.FotoRecuperada;
import com.algaworks.algafood.domain.service.ProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
	
	@Autowired
	private FotoProdutoService fotoProdutoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private FotoProdutoConverter fotoProdutoConverter;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoResponse buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		FotoProduto fotoProduto = fotoProdutoService.buscar(produtoId, restauranteId);
		
		return fotoProdutoConverter.toResponseDto(fotoProduto);
	}
	
	@GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<?> buscarArquivo(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = fotoProdutoService.buscar(produtoId, restauranteId);
			
			verificarCompatibilidadeMediaType(fotoProduto.getContentType(), acceptHeader);
			
			FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			
			if (fotoRecuperada.temUrl()) {
				return ResponseEntity.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			} else {
				return ResponseEntity.ok()
						.contentType(MediaType.parseMediaType(fotoProduto.getContentType()))
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoResponse atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoRequest fotoProdutoResquestDto) throws IOException {
		Produto produto = produtoService.buscar(produtoId, restauranteId);
		
		FotoProduto fotoProduto = new FotoProduto();
		fotoProduto.setProduto(produto);
		fotoProduto.setDescricao(fotoProdutoResquestDto.getDescricao());
		fotoProduto.setContentType(fotoProdutoResquestDto.getMultipartFile().getContentType());
		fotoProduto.setTamanho(fotoProdutoResquestDto.getMultipartFile().getSize());
		fotoProduto.setNomeArquivo(fotoProdutoResquestDto.getMultipartFile().getOriginalFilename());
		
		fotoProduto = fotoProdutoService.salvar(fotoProduto, fotoProdutoResquestDto.getMultipartFile().getInputStream());
		
		return fotoProdutoConverter.toResponseDto(fotoProduto);
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		fotoProdutoService.remover(produtoId, restauranteId);
	}
	
	private void verificarCompatibilidadeMediaType(String contentType, String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		MediaType mediaType = MediaType.parseMediaType(contentType);
		List<MediaType> mediaTypes = MediaType.parseMediaTypes(acceptHeader);
		
		boolean compativel = mediaTypes.stream().anyMatch(media -> media.isCompatibleWith(mediaType));
		
		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypes);
		}
	}
	
}
