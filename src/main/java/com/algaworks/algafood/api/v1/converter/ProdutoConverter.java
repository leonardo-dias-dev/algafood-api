package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.dto.input.ProdutoInput;
import com.algaworks.algafood.api.v1.dto.model.ProdutoModel;
import com.algaworks.algafood.core.security.resourceserver.AlgaSecurity;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoConverter extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public ProdutoConverter() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    public Produto toDomainObject(ProdutoInput produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }

    public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    }

    @Override
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());

        modelMapper.map(produto, produtoModel);

        if (algaSecurity.podeConsultarRestaurantes()) {
            produtoModel.add(
                    algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos")
            );

            produtoModel.add(
                    algaLinks.linkToFotoProduto(produto.getRestaurante().getId(), produto.getId(), "foto")
            );
        }

        return produtoModel;
    }

    /*public List<ProdutoModel> toCollectionModel(List<Produto> produtos) {
        return produtos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }*/

}
