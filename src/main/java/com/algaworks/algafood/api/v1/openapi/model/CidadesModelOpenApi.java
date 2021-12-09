package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.dto.model.CidadeModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
@ApiModel("CidadesModel")
public class CidadesModelOpenApi {

    private CidadeEmbeddedModelOpenApi _embedded;

    private Links _links;

    @Data
    @ApiModel("CidadesEmbeddedModel")
    private class CidadeEmbeddedModelOpenApi {

        private List<CidadeModel> cidades;

    }

}
