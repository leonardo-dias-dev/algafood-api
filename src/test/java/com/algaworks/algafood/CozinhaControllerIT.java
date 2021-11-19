package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class CozinhaControllerIT extends TestApi {

    private static final int ID_COZINHA_INEXISTENE = 1000000;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private int quantidadeCozinhasCadastradas;

    private Cozinha cozinhaExistente;

    @Before
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();

        prepararDados();
    }

    private void prepararDados() {
        Cozinha tailandesa = new Cozinha();
        tailandesa.setNome("Tailandesa");

        Cozinha americana = new Cozinha();
        americana.setNome("Americana");

        cozinhaRepository.save(tailandesa);
        cozinhaRepository.save(americana);

        quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();

        cozinhaExistente = tailandesa;
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConterCozinhas_QuandoConsultarCozinhas() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", Matchers.hasSize(quantidadeCozinhasCadastradas));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {
        String cozinhaJson = ResourceUtils.getContentFromResource("/json/cozinha.json");

        RestAssured.given()
                .body(cozinhaJson)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
        RestAssured.given()
                .pathParam("id", cozinhaExistente.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", Matchers.equalTo(cozinhaExistente.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
        RestAssured.given()
                .pathParam("id", ID_COZINHA_INEXISTENE)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
