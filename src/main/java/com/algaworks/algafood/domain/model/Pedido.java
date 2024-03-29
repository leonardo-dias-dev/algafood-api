package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.exception.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Pedido extends AbstractAggregateRoot<Pedido> {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 36)
    private String codigo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal taxaFrete;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataCancelamento;

    private OffsetDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StatusPedido status = StatusPedido.CRIADO;

    @Embedded
    private Endereco enderecoEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        setCodigo(UUID.randomUUID().toString());
    }

    public void calcularValorTotal() {
        itens.forEach(ItemPedido::calcularPrecoTotal);

        subtotal = itens.stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        valorTotal = subtotal.add(taxaFrete);
    }

    public void confirmar() {
        setStatus(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());

        registerEvent(new PedidoConfirmadoEvent(this));
    }

    public void entregar() {
        setStatus(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());

        registerEvent(new PedidoCanceladoEvent(this));
    }

    private void setStatus(StatusPedido statusPedido) {
        if (status.naoPodeAlterarPara(statusPedido)) {
            throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s", codigo, status, statusPedido.getDescricao()));
        }

        status = statusPedido;
    }

    public boolean podeConfirmar() {
        return getStatus().podeAlterarPara(StatusPedido.CONFIRMADO);
    }

    public boolean podeEntregar() {
        return getStatus().podeAlterarPara(StatusPedido.ENTREGUE);
    }

    public boolean podeCancelar() {
        return getStatus().podeAlterarPara(StatusPedido.CANCELADO);
    }

}
