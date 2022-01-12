package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.converter.PedidoConverter;
import com.algaworks.algafood.api.v1.converter.PedidoResumoConverter;
import com.algaworks.algafood.api.v1.dto.input.PedidoInput;
import com.algaworks.algafood.api.v1.dto.model.PedidoModel;
import com.algaworks.algafood.api.v1.dto.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.jpa.PageWrapper;
import com.algaworks.algafood.core.jpa.PageableTranslator;
import com.algaworks.algafood.core.security.resourceserver.AlgaSecurity;
import com.algaworks.algafood.core.security.resourceserver.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.domain.service.PedidoService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private PedidoConverter pedidoConverter;

    @Autowired
    private PedidoResumoConverter pedidoResumoConverter;

    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @Autowired
    private AlgaSecurity algaSecurity;

    @Override
    @GetMapping
    @CheckSecurity.Pedidos.Pesquisar
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter pedidoFilter, @PageableDefault(size = 10) Pageable pageable) {
        Pageable pageableTraduzido = traduzirPageable(pageable);

        Page<Pedido> pedidoPage = pedidoService.pesquisar(pedidoFilter, pageableTraduzido);

        pedidoPage = new PageWrapper<>(pedidoPage, pageable);

        return pagedResourcesAssembler.toModel(pedidoPage, pedidoResumoConverter);
    }

    @Override
    @CheckSecurity.Pedidos.Buscar
    @GetMapping(path = "/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        Pedido pedido = pedidoService.buscar(codigoPedido);

        return pedidoConverter.toModel(pedido);
    }

    @Override
    @PostMapping
    @CheckSecurity.Pedidos.Criar
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido pedido = pedidoConverter.toDomainObject(pedidoInput);

            pedido.setCliente(new Usuario());
            pedido.getCliente().setId(algaSecurity.getUsuarioId());

            pedido = emissaoPedidoService.emitir(pedido);

            return pedidoConverter.toModel(pedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable traduzirPageable(Pageable pageable) {
        var mapeamento = ImmutableMap.of(
                "codigo", "codigo",
                "nomeRestaurante", "restaurante.nome",
                "nomeCliente", "cliente.nome",
                "valorTotal", "valorTotal"
        );

        return PageableTranslator.translate(pageable, mapeamento);
    }

}
