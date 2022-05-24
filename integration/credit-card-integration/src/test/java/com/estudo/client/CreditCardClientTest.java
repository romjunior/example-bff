package com.estudo.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.time.LocalDate;

@WebFluxTest
@AutoConfigureWireMock
class CreditCardClientTest {

    private final CreditCardClient client;

    public CreditCardClientTest(@Value("${credit-card.url}") String baseUrl) {
        client = new CreditCardClient(WebClient.builder(), baseUrl);
    }

    @Test
    void test() {
        StepVerifier.create(client.getAllCards(1))
                .expectNext(new CardDTO(1, "3233****43241", "João Costa Henrique", LocalDate.of(2024, 10, 2)))
                .expectNext(new CardDTO(2, "5404****40202", "João Costa Henrique", LocalDate.of(2023, 10, 2)))
                .expectNextCount(0)
                .verifyComplete();
    }
}
