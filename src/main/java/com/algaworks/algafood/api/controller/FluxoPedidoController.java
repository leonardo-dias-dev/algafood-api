package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.openapi.controller.FluxoPedidoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.service.FluxoPedidoService;

@RestController
@RequestMapping(path = "/pedidos/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {
	
	@Autowired
	private FluxoPedidoService fluxoPedidoService;
	
	@Override
    @PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmar(@PathVariable String codigo) {
		fluxoPedidoService.confirmar(codigo);
	}
	
	@Override
    @PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void entregar(@PathVariable String codigo) {
		fluxoPedidoService.entregar(codigo);
	}
	
	@Override
    @PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable String codigo) {
		fluxoPedidoService.cancelar(codigo);
	}
	
}
