package com.algaworks.algafood.api.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteIdRequest {
	
	@NotNull
	private Long id;
	
}
