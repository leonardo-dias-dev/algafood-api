package com.algaworks.algafood.api.v1.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProdutoIdInput {

    @NotNull
    private Long id;

}
