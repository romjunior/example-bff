package com.estudo.controller;

import com.estudo.service.CreditCardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@WebFluxTest(CreditCardController.class)
class CreditCardControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CreditCardService service;

    @Test
    void shouldReturnOk() {

        Mockito.when(service.listAllCreditCardsFromUser(1)).thenReturn(Flux.empty());

        WebTestClient.ResponseSpec responseSpec = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/cards").queryParam("userId", "1").build())
                .exchange();

        responseSpec.expectStatus().isOk();
    }
}
