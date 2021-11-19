package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.FormaPagamentoConverter;
import com.algaworks.algafood.api.dto.input.FormaPagamentoInput;
import com.algaworks.algafood.api.dto.model.FormaPagamentoModel;
import com.algaworks.algafood.api.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoConverter formaPagamentoConverter;

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest servletWebRequest) {
        String eTag = eTag(servletWebRequest);

        if (servletWebRequest.checkNotModified(eTag)) {
            return null;
        }

        List<FormaPagamento> formaPagamentos = formaPagamentoService.listar();

        CollectionModel<FormaPagamentoModel> formasPagamentosResponse = formaPagamentoConverter.toCollectionModel(formaPagamentos);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(formasPagamentosResponse);
    }

    private String eTag(ServletWebRequest servletWebRequest) {
        ShallowEtagHeaderFilter.disableContentCaching(servletWebRequest.getRequest());
        String eTag = "0";
        Optional<OffsetDateTime> dataUltimaAtualizacao = formaPagamentoService.getDataUltimaAtualizacao();

        if (dataUltimaAtualizacao.isPresent()) {
            eTag = String.valueOf(dataUltimaAtualizacao.get().toEpochSecond());
        }

        return eTag;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long id, ServletWebRequest servletWebRequest) {
        String eTag = eTag(servletWebRequest);

        if (servletWebRequest.checkNotModified(eTag)) {
            return null;
        }

        FormaPagamento formaPagamento = formaPagamentoService.buscar(id);

        FormaPagamentoModel formaPagamentoModel = formaPagamentoConverter.toModel(formaPagamento);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamentoModel);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@Valid @RequestBody FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoConverter.toDomainObject(formaPagamentoInput);

        formaPagamento = formaPagamentoService.salvar(formaPagamento);

        return formaPagamentoConverter.toModel(formaPagamento);
    }

    @Override
    @PutMapping("/{id}")
    public FormaPagamentoModel atualizar(@PathVariable Long id, @Valid @RequestBody FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoService.buscar(id);

        formaPagamentoConverter.copyToDomainObject(formaPagamentoInput, formaPagamento);

        formaPagamento = formaPagamentoService.salvar(formaPagamento);

        return formaPagamentoConverter.toModel(formaPagamento);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        formaPagamentoService.remover(id);
    }

}
