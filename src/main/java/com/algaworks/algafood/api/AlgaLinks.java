package com.algaworks.algafood.api;

import com.algaworks.algafood.api.controller.*;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class AlgaLinks {

    private static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public Link linkToPedidos() {
        return linkToPedidos(IanaLinkRelations.SELF.value());
    }

    public Link linkToPedidos(String rel) {
        TemplateVariables filtersVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );


        return new Link(
                UriTemplate.of(
                        WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString(),
                        PAGINACAO_VARIABLES.concat(filtersVariables)
                ), rel);
    }

    public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
                        .confirmar(codigoPedido)
        ).withRel(rel);
    }

    public Link linkToEntregaPedido(String codigoPedido, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
                        .entregar(codigoPedido)
        ).withRel(rel);
    }

    public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(FluxoPedidoController.class)
                        .cancelar(codigoPedido)
        ).withRel(rel);
    }

    public Link linkToRestaurante(Long restauranteId) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RestauranteController.class)
                        .buscar(restauranteId)
        ).withSelfRel();
    }

    public Link linkToUsuarios(String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CidadeController.class)
                        .listar()
        ).withRel(rel);
    }

    public Link linkToUsuarios() {
        return linkToUsuarios(IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuario(Long usuarioId) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioController.class)
                        .buscar(usuarioId)
        ).withSelfRel();
    }

    public Link linkToFormaPagamento(Long formaPagamentoId) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(FormaPagamentoController.class)
                        .buscar(formaPagamentoId, null)
        ).withSelfRel();
    }

    public Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.SELF.value());
    }

    public Link linkToCidades(String rel) {
        return WebMvcLinkBuilder.linkTo(CidadeController.class)
                .withRel(rel);
    }

    public Link linkToCidade(Long cidadeId) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CidadeController.class)
                        .buscar(cidadeId)
        ).withSelfRel();
    }

    public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class)
                        .buscar(restauranteId, produtoId)
        ).withRel(rel);
    }

    public Link linkToEstados(String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(EstadoController.class)
                        .listar()
        ).withRel(rel);
    }

    public Link linkToEstados() {
        return linkToEstados(IanaLinkRelations.SELF.value());
    }

    public Link linkToEstado(Long estadoId) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(EstadoController.class)
                        .buscar(estadoId)
        ).withSelfRel();
    }

    public Link linkToCozinhas(String rel) {
        return new Link(
                UriTemplate.of(
                        WebMvcLinkBuilder.linkTo(CozinhaController.class).toUri().toString(),
                        PAGINACAO_VARIABLES
                ), rel);
    }

    public Link linkToGrupos(Long usuarioId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class)
                        .listar(usuarioId)
        ).withRel(rel);
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class)
                        .listar(restauranteId)
        ).withRel(rel);
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId) {
        return linkToResponsaveisRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantes(String rel) {
        String restauranteUri = WebMvcLinkBuilder.linkTo(RestauranteController.class).toUri().toString();

        return new Link(UriTemplate.of(restauranteUri, PROJECAO_VARIABLES), rel);
    }

    public Link linkToRestaurantes() {
        return linkToRestaurantes(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class)
                        .listar(restauranteId)
        ).withRel(rel);
    }

    public Link linkToCozinha(Long cozinhaId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CozinhaController.class)
                        .buscar(cozinhaId)
        ).withRel(rel);
    }

    public Link linkToCozinha(Long cozinhaId) {
        return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteAbertura(Long restauranteId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RestauranteController.class)
                        .abrir(restauranteId)
        ).withRel(rel);
    }

    public Link linkToRestauranteFechamento(Long restauranteId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RestauranteController.class)
                        .fechar(restauranteId)
        ).withRel(rel);
    }

    public Link linkToRestauranteInativacao(Long restauranteId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RestauranteController.class)
                        .inativar(restauranteId)
        ).withRel(rel);
    }

    public Link linkToRestauranteAtivacao(Long restauranteId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RestauranteController.class)
                        .ativar(restauranteId)
        ).withRel(rel);
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId) {
        return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormaPagamentoDesassociacao(Long restauranteId, Long formaPagamentoId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class)
                        .desassociar(restauranteId, formaPagamentoId)
        ).withRel(rel);
    }

    public Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class)
                        .associar(restauranteId, null)
        ).withRel(rel);
    }

    public Link linkToFormasPagamento(String rel) {
        return WebMvcLinkBuilder.linkTo(FormaPagamentoController.class)
                .withRel(rel);
    }

    public Link linkToFormasPagamento() {
        return linkToFormasPagamento(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteResponsavelDesassociacao(Long restauranteId, Long usuarioId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class)
                        .desassociar(restauranteId, usuarioId)
        ).withRel(rel);
    }

    public Link linkToRestauranteResponsavelAssociacao(Long restauranteId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class)
                        .associar(restauranteId, null)
        ).withRel(rel);
    }

    public Link linkToProdutos(Long restauranteId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class)
                        .listar(restauranteId, null)
        ).withRel(rel);
    }

    public Link linkToProdutos(Long restaurtanteId) {
        return linkToProdutos(restaurtanteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFotoProduto(Long restauranteId, Long produtoId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RestauranteProdutoFotoController.class)
                        .buscar(restauranteId, produtoId)
        ).withRel(rel);
    }

    public Link linkToFotoProduto(Long restauranteId, Long produtoId) {
        return linkToFotoProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupos(String rel) {
        return WebMvcLinkBuilder.linkTo(GrupoController.class).withRel(rel);
    }

    public Link linkToGrupos() {
        return linkToGrupos(IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupoPermissoes(Long grupoId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(GrupoPermissaoController.class)
                        .listar(grupoId)
        ).withRel(rel);
    }

    public Link linkToPermissoes(String rel) {
        return WebMvcLinkBuilder.linkTo(PermissaoController.class).withRel(rel);
    }

    public Link linkToPermissoes() {
        return linkToPermissoes(IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupoPermissoes(Long grupoId) {
        return linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupoPermissaoAssociacao(Long grupoId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(GrupoPermissaoController.class)
                        .associar(grupoId, null)
        ).withRel(rel);
    }

    public Link linkToGrupoPermissaoDesassociacao(Long grupoId, Long permissaoId, String rel) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(GrupoPermissaoController.class)
                        .desassociar(grupoId, permissaoId)
        ).withRel(rel);
    }

    public Link linkToUsuarioGrupoAssociacao(Long usuarioId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class)
                .associar(usuarioId, null)).withRel(rel);
    }

    public Link linkToUsuarioGrupoDesassociacao(Long usuarioId, Long grupoId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class)
                .desassociar(usuarioId, grupoId)).withRel(rel);
    }

    public Link linkToEstatisticas(String rel) {
        return WebMvcLinkBuilder.linkTo(EstatisticasController.class).withRel(rel);
    }

    public Link linkToEstatisticasVendasDiarias(String rel) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("timeOffset", TemplateVariable.VariableType.REQUEST_PARAM));

        String pedidosUrl = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstatisticasController.class)
                .consultarVendasDiarias(null, null)).toUri().toString();

        return new Link(UriTemplate.of(pedidosUrl, filtroVariables), rel);
    }

}
