package com.algaworks.algafood.api.v1.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioComSenhaInput extends UsuarioInput {

    @NotBlank
    @ApiModelProperty(example = "123456", required = true)
    private String senha;

}
