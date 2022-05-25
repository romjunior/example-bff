package com.estudo.controller;

import com.estudo.JSONHelper;
import com.estudo.domain.CreditCard;
import com.estudo.service.CreditCardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebFluxTest(CreditCardController.class)
class CreditCardControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreditCardService service;

    @Test
    @DisplayName("Deve retornar OK com a lista de cartões de crédito")
    void shouldReturnOk() throws JsonProcessingException {
        Mockito.when(service.listAllCreditCardsFromUser(1)).thenReturn(getFluxCreditCards());
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cards")
                        .queryParam("userId", "1")
                        .build()
                )
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().json(objectMapper.writeValueAsString(getCreditCardResponses()));
    }

    @Test
    @DisplayName("Deve retornar Ok com a lista vazia")
    void shouldReturnOkNoContent() throws JsonProcessingException {
        Mockito.when(service.listAllCreditCardsFromUser(1)).thenReturn(Flux.empty());
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cards")
                        .queryParam("userId", "1")
                        .build()
                )
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().json(objectMapper.writeValueAsString(List.of()));
    }

    @Test
    @DisplayName("Deve retornar resposta 500 com path e mensagem de error")
    void shouldReturn500Error() throws IOException {
        Mockito.when(service.listAllCreditCardsFromUser(1)).thenReturn(Flux.error(new Exception()));
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cards")
                        .queryParam("userId", "1")
                        .build()
                )
                .exchange()
                .expectStatus()
                .is5xxServerError()
                .expectBody().json(JSONHelper.getData(objectMapper, "error-500.json", String.class));
    }

    private Flux<CreditCard> getFluxCreditCards() {
        return Flux.fromIterable(getCreditCards());
    }

    private List<CreditCardResponse> getCreditCardResponses() {
        return getCreditCards()
                .stream()
                .map(CreditCardResponse::of)
                .toList();
    }

    private List<CreditCard> getCreditCards() {
        return List.of(new CreditCard(1,
                        "3233****43241",
                        "João Costa Henrique",
                        LocalDate.of(2024, 10, 2),
                        null,
                        List.of()
                ),
                new CreditCard(2,
                        "5404****40202",
                        "João Costa Henrique",
                        LocalDate.of(2023, 10, 2),
                        null,
                        List.of()
                ));
    }


}
