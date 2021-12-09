package com.algaworks.algafood.api.v1.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class ItemPedidoInput {

    @NotNull
    @PositiveOrZero
    private Integer quantidade;

    private String observacao;

    private ProdutoIdInput produto;

}
