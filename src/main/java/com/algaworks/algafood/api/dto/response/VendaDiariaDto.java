package com.algaworks.algafood.api.dto.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VendaDiariaDto {
	
	private Date data;
	
	private Long totalVendas;
	
	private BigDecimal totalFaturado;
	
}
