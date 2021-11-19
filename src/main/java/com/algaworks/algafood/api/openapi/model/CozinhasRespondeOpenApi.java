package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.model.CozinhaModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Setter
@Getter
@ApiModel("CozinhasResponse")
public class CozinhasRespondeOpenApi {

    private CozinhasEmbeddedModelOpenApi _embedded;

    private Links _links;

    private PageModelOpenApi page;

    @Setter
    @Getter
    @ApiModel("CozinhasEmbeddedModel")
    public class CozinhasEmbeddedModelOpenApi {

        private List<CozinhaModel> cozinhas;

    }

}
