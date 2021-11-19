package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ProdutoNaoEncontradoException(String msg) {
        super(msg);
    }

    public ProdutoNaoEncontradoException(Long id) {
        this(String.format("Não existe um cadastro de produto com código %d", id));
    }

}
