package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.annotation.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String nome;

    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @Column(nullable = false)
    private Boolean ativo = Boolean.TRUE;

    @Column(nullable = false)
    private Boolean aberto = Boolean.FALSE;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formaPagamentos = new HashSet<>();

    @OneToMany(mappedBy = "restaurante")
    private Set<Produto> produtos = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> responsaveis = new HashSet<>();

    public void ativar() {
        setAtivo(Boolean.TRUE);
    }

    public void inativar() {
        setAtivo(Boolean.FALSE);
    }

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
        return formaPagamentos.add(formaPagamento);
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
        return formaPagamentos.remove(formaPagamento);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
        return formaPagamentos.contains(formaPagamento);
    }

    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
        return !formaPagamentos.contains(formaPagamento);
    }

    public boolean adicionarProduto(Produto produto) {
        return produtos.add(produto);
    }

    public boolean removerProduto(Produto produto) {
        return produtos.remove(produto);
    }

    public void abrir() {
        setAberto(Boolean.TRUE);
    }

    public void fechar() {
        setAberto(Boolean.FALSE);
    }

    public boolean adicionarResponsavel(Usuario usuario) {
        return responsaveis.add(usuario);
    }

    public boolean removerResponsavel(Usuario usuario) {
        return responsaveis.remove(usuario);
    }

    public boolean isAberto() {
        return this.aberto;
    }

    public boolean isFechado() {
        return !isAberto();
    }

    public boolean isAtivo() {
        return this.ativo;
    }

    public boolean isInativo() {
        return !isAtivo();
    }

    public boolean aberturaPermitida() {
        return isAtivo() && isFechado();
    }

    public boolean fechamentoPermitido() {
        return isAberto();
    }

    public boolean ativacaoPermitida() {
        return isInativo();
    }

    public boolean inativacaoPermitida() {
        return isAtivo();
    }

}
