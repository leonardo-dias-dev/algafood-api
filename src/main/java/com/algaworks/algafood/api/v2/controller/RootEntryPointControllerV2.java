package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.v1.dto.model.RootEntryPointModel;
import com.algaworks.algafood.api.v2.AlgaLinksV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v2",produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointControllerV2 {

    @Autowired
    private AlgaLinksV2 algaLinksV2;

    @GetMapping
    public RootEntryPointModel root() {
        RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(algaLinksV2.linkToCozinhas("cozinhas"));
        rootEntryPointModel.add(algaLinksV2.linkToCidades("cidades"));

        return rootEntryPointModel;
    }

}
