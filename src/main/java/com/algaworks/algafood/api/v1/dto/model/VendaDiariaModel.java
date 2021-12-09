package com.algaworks.algafood.api.v1.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class VendaDiariaModel {

    private Date data;

    private Long totalVendas;

    private BigDecimal totalFaturado;

}
