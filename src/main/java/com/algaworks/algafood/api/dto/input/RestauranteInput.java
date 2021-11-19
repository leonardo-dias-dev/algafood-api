package com.algaworks.algafood.api.dto.input;

import com.algaworks.algafood.core.validation.annotation.TaxaFrete;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteInput {

    @NotBlank
    @ApiModelProperty(example = "Thai Gourmet", required = true)
    private String nome;

    @NotNull
    @TaxaFrete
    @ApiModelProperty(example = "12.50", required = true)
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaIdInput cozinha;

    @Valid
    @NotNull
    private EnderecoInput endereco;

}
