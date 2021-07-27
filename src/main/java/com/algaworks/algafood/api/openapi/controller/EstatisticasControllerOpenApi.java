package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.response.VendaDiariaDto;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {

    @ApiOperation("Consulta estatísticas de vendas diárias")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restauranteId", value = "ID do restaurante", example = "1", required = true),
            @ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/Hora inicial da criação do pedido", example = "2019-12-01T00:00:00Z", dataType = "date-time"),
            @ApiImplicitParam(name = "dataCriacaoFim", value = "Data/Hora final da criação do pedido", example = "2019-12-02T23:59:59Z", dataType = "date-time")
    })
    List<VendaDiariaDto> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter,
                                                @ApiParam(value = "Deslocamento de horário a ser considerado na consulta em relação ao UTC", defaultValue = "+00:00") String timeOffset);


    ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter vendaDiariaFilter, String timeOffset);

}
