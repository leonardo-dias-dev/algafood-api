package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class FotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    public FotoProduto buscar(Long produtoId, Long restauranteId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradoException(restauranteId, produtoId));
    }

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto, InputStream inputStream) {
        validarSeExisteFotoProduto(fotoProduto);

        gerarNomeArquivo(fotoProduto, inputStream);

        fotoProduto = produtoRepository.save(fotoProduto);
        produtoRepository.flush();

        armazenarArquivo(fotoProduto, inputStream);

        return fotoProduto;
    }

    @Transactional
    public void remover(Long produtoId, Long restauranteId) {
        FotoProduto fotoProduto = buscar(produtoId, restauranteId);

        produtoRepository.deleteFotoProduto(fotoProduto);

        fotoStorageService.remover(fotoProduto.getNomeArquivo());
    }

    private void validarSeExisteFotoProduto(FotoProduto fotoProduto) {
        Optional<FotoProduto> optional = produtoRepository.findFotoById(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId());

        if (optional.isPresent()) {
            produtoRepository.deleteFotoProduto(optional.get());

            fotoStorageService.remover(optional.get().getNomeArquivo());
        }
    }

    private void gerarNomeArquivo(FotoProduto fotoProduto, InputStream inputStream) {
        String nomeArquivo = fotoStorageService.gerarNomeArquivo(fotoProduto.getNomeArquivo());

        fotoProduto.setNomeArquivo(nomeArquivo);
    }

    private void armazenarArquivo(FotoProduto fotoProduto, InputStream inputStream) {
        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(fotoProduto.getNomeArquivo())
                .inputStream(inputStream)
                .contentType(fotoProduto.getContentType())
                .build();

        fotoStorageService.armazenar(novaFoto);
    }

}
