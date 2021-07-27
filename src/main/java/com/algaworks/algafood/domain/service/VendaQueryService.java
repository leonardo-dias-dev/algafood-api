package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.api.dto.response.VendaDiariaDto;

public interface VendaQueryService {
	
	List<VendaDiariaDto> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffset);

}
