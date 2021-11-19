package com.algaworks.algafood.api.exceptionhandler.detail;

import lombok.Getter;

@Getter
public enum ProblemType {

    RECURSO_NAO_ENCONTRADO("Recurso não encontrado", "/recurso-nao-encontrado"),
    ERRO_NEGOCIO("Violação da regra de negócio", "/erro-negocio"),
    ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
    MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensível", "/mensagem-incompreensivel"),
    PARAMETRO_INVALIDO("Parâmetro inválido", "/parametro-invalido"),
    ERRO_DE_SISTEMA("Erro de sistema", "/erro-de-sistema"),
    DADOS_INVALIDOS("Dados inválidos", "/dados-invalidos");

    private String title;

    private String uri;

    private ProblemType(String title, String uri) {
        this.title = title;
        this.uri = uri;
    }

}
