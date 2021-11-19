package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.dto.model.VendaDiariaModel;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiariaModel> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffset);

}
