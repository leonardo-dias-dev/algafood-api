package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDto;

public interface VendaQueryService {
	
	List<VendaDiariaDto> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffset);

}
