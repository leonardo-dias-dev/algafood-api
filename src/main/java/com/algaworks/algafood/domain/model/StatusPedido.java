package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

    CRIADO("Criado"),
    CONFIRMADO("Confirmado", CRIADO),
    ENTREGUE("Entregue", CONFIRMADO),
    CANCELADO("Cancelado", CRIADO);

    private final String descricao;

    private final List<StatusPedido> statusAnteriores;

    private StatusPedido(String descricao, StatusPedido... statusAnteriores) {
        this.descricao = descricao;
        this.statusAnteriores = Arrays.asList(statusAnteriores);
    }

    public boolean naoPodeAlterarPara(StatusPedido statusPedido) {
        return !statusPedido.statusAnteriores.contains(this);
    }

    public boolean podeAlterarPara(StatusPedido statusPedido) {
        return !naoPodeAlterarPara(statusPedido);
    }

    public String getDescricao() {
        return descricao;
    }

}
