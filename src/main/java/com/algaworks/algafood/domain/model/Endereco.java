package com.algaworks.algafood.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable
public class Endereco {

    @Column(name = "endereco_cep", length = 9, nullable = false)
    private String cep;

    @Column(name = "endereco_logradouro", length = 100, nullable = false)
    private String logradouro;

    @Column(name = "endereco_numero", length = 20, nullable = false)
    private String numero;

    @Column(name = "endereco_complemento", length = 60)
    private String complemento;

    @Column(name = "endereco_bairro", length = 60, nullable = false)
    private String bairro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_cidade_id", nullable = false)
    private Cidade cidade;

}
