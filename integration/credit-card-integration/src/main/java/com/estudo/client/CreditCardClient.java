package com.estudo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
class CreditCardClient {

    private final WebClient client;

    public CreditCardClient(
            final WebClient.Builder builder,
            @Value("${credit-card.url}") final String baseUrl
    ) {
        client = builder.baseUrl(baseUrl)
                .build();
    }

    Flux<CardDTO> getAllCards(final int userId) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/card").
                        queryParam("userId", userId)
                        .build())
                .retrieve()
                .bodyToFlux(CardDTO.class);
    }

}
