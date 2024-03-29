package com.algaworks.algafood.api.v1.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeIdInput {

    @NotNull
    @ApiModelProperty(example = "1", required = true)
    private Long id;

}
