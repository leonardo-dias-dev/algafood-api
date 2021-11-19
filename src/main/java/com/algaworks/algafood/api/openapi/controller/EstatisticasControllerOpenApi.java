package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.model.EstatisticasModel;
import com.algaworks.algafood.api.dto.model.VendaDiariaModel;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {

    @ApiOperation(value = "Estatísticas", hidden = true)
    EstatisticasModel estatisticas();

    @ApiOperation("Consulta estatísticas de vendas diárias")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restauranteId", value = "ID do restaurante", example = "1", required = true),
            @ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/Hora inicial da criação do pedido", example = "2019-12-01T00:00:00Z", dataType = "date-time"),
            @ApiImplicitParam(name = "dataCriacaoFim", value = "Data/Hora final da criação do pedido", example = "2019-12-02T23:59:59Z", dataType = "date-time")
    })
    List<VendaDiariaModel> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter,
                                                  @ApiParam(value = "Deslocamento de horário a ser considerado na consulta em relação ao UTC", defaultValue = "+00:00") String timeOffset);


    ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter vendaDiariaFilter, String timeOffset);

}
