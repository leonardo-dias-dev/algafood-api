package com.algaworks.algafood.api.v1.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EstadoInput {

    @NotBlank
    @ApiModelProperty(example = "Mato Grosso", required = true)
    private String nome;

}
