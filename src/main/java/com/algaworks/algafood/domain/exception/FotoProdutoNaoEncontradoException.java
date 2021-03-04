package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public FotoProdutoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public FotoProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
		this(String.format("Não existe um cadastro de foto do produto com o código %d para o restaurante de código %s", produtoId, restauranteId));
	}

}
