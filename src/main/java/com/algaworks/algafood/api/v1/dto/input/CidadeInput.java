package com.algaworks.algafood.api.v1.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInput {

    @NotBlank
    @ApiModelProperty(example = "Uberlândia", required = true)
    private String nome;

    @Valid
    @NotNull
    private EstadoIdInput estado;

}
