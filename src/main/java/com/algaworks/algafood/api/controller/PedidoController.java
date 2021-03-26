package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.dto.request.PedidoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.dto.converter.PedidoConverter;
import com.algaworks.algafood.api.dto.converter.PedidoResumoConverter;
import com.algaworks.algafood.api.dto.response.PedidoResponse;
import com.algaworks.algafood.api.dto.response.PedidoResumoResponse;
import com.algaworks.algafood.core.jpa.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.domain.service.PedidoService;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Autowired
	private PedidoConverter pedidoConverter;
	
	@Autowired
	private PedidoResumoConverter pedidoResumoConverter;
	
	@GetMapping
	public Page<PedidoResumoResponse> pesquisar(PedidoFilter pedidoFilter, Pageable pageable) {
		pageable = traduzirPageable(pageable);
		
		Page<Pedido> pedidoPage = pedidoService.pesquisar(pedidoFilter, pageable);
		
		List<PedidoResumoResponse> pedidoResponsePage = pedidoResumoConverter.toCollectionResponseDto(pedidoPage.getContent());
		
		Page<PedidoResumoResponse> pedidoResumoPage = new PageImpl<>(pedidoResponsePage, pageable, pedidoPage.getTotalElements());
		
		return pedidoResumoPage;
	}
	
	@GetMapping("/{codigo}")
	public PedidoResponse buscar(@PathVariable String codigo) {
		Pedido pedido = pedidoService.buscar(codigo);
		
		return pedidoConverter.toResponseDto(pedido);
	}
	
	@PostMapping
	public PedidoResponse adicionar(@Valid @RequestBody PedidoRequest pedidoRequest) {
		try {
			Pedido pedido = pedidoConverter.toEntity(pedidoRequest);
			
			// TODO - Pegar usuário autenticado
			pedido.setCliente(new Usuario());
			pedido.getCliente().setId(1L);
			
			pedido = emissaoPedidoService.emitir(pedido);
			
			return pedidoConverter.toResponseDto(pedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	private Pageable traduzirPageable(Pageable pageable) {
		var mapeamento = ImmutableMap.of(
				"codigo", "codigo",
				"restaurante.nome", "restaurante.nome",
				"cliente.nome", "cliente.nome",
				"valorTotal", "valorTotal"
				);
		
		return PageableTranslator.translate(pageable, mapeamento);
	}
	
}
