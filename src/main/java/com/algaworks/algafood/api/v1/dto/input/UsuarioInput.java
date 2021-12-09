package com.algaworks.algafood.api.v1.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioInput {

    @NotBlank
    @ApiModelProperty(example = "José da Silva", required = true)
    private String nome;

    @Email
    @NotBlank
    @ApiModelProperty(example = "jose.silva@algafood.com.br", required = true)
    private String email;

}
