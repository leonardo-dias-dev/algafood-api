package com.algaworks.algafood.api.v1.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Getter
@Setter
@Relation(collectionRelation = "restaurantes")
public class RestauranteModel extends RepresentationModel<RestauranteModel> {

    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private Boolean ativo;

    private Boolean aberto;

    private CozinhaModel cozinha;

    private EnderecoModel endereco;

}
