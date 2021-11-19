package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.dto.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoConverter extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PedidoResumoConverter() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido entity) {
        PedidoResumoModel pedidoResumoModel = createModelWithId(entity.getCodigo(), entity);

        modelMapper.map(entity, pedidoResumoModel);

        pedidoResumoModel.add(algaLinks.linkToPedidos("pedidos"));

        pedidoResumoModel.getRestaurante().add(algaLinks.linkToRestaurante(pedidoResumoModel.getRestaurante().getId()));

        pedidoResumoModel.getCliente().add(algaLinks.linkToUsuario(pedidoResumoModel.getCliente().getId()));

        return pedidoResumoModel;
    }
}
