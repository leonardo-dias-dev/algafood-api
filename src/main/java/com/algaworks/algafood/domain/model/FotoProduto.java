package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FotoProduto {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "produto_id")
    private Long id;

    private String nomeArquivo;

    private String descricao;

    private String contentType;

    private Long tamanho;

    @MapsId
    @OneToOne
    private Produto produto;

    public Long getRestauranteId() {
        return getProduto() == null ? null : getProduto().getRestaurante().getId();
    }

}
