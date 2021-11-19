package com.algaworks.algafood.api.exceptionhandler.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * ProblemDetails
 */
@Getter
@Builder
@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(example = "2019-12-01T18:09:02.70844Z", position = 5)
    private OffsetDateTime timestamp;

    @ApiModelProperty(example = "https://algafood.com.br/dados-invalidos", position = 10)
    private String type;

    @ApiModelProperty(example = "Dados invávlidos", position = 15)
    private String title;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos", position = 20)
    private String detail;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos", position = 20)
    private String userMessage;

    @ApiModelProperty(value = "Objetos ou campos que geraram o erro", position = 25)
    private List<Object> objects;

    @Getter
    @Builder
    @ApiModel("ObjetoProblema")
    public static class Object {

        @ApiModelProperty(example = "preco", position = 1)
        private String name;

        @ApiModelProperty(example = "O preço é obrigatório", position = 5)
        private String userMessage;

    }

}