package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listar(Restaurante restaurante) {
        return produtoRepository.findAtivosByRestaurante(restaurante);
    }

    public List<Produto> listar(Restaurante restaurante, Boolean incluirInativos) {
        return incluirInativos
                ? produtoRepository.findAllByRestaurante(restaurante)
                : produtoRepository.findAtivosByRestaurante(restaurante);
    }

    public Produto buscar(Long produtoId, Long restauranteId) {
        return produtoRepository.findById(produtoId, restauranteId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
    }

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

}
