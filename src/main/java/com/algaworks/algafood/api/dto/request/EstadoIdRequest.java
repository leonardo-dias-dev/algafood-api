package com.algaworks.algafood.api.dto.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIdRequest {
	
	@NotNull
	@ApiModelProperty(example = "1", required = true)
	private Long id;
	
}
