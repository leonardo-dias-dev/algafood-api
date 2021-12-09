package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.dto.model.RootEntryPointModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1",produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping
    public RootEntryPointModel root() {
        RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(algaLinks.linkToCozinhas("cozinhas"));
        rootEntryPointModel.add(algaLinks.linkToPedidos("pedidos"));
        rootEntryPointModel.add(algaLinks.linkToRestaurantes("restaurantes"));
        rootEntryPointModel.add(algaLinks.linkToGrupos("grupos"));
        rootEntryPointModel.add(algaLinks.linkToPermissoes("permissoes"));
        rootEntryPointModel.add(algaLinks.linkToFormasPagamento("formas-pagamento"));
        rootEntryPointModel.add(algaLinks.linkToCidades("cidades"));
        rootEntryPointModel.add(algaLinks.linkToEstatisticas("estatisticas"));

        return rootEntryPointModel;
    }

}
