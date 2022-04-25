package com.estudo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
class TrackingClient {

    private final WebClient client;

    public TrackingClient(WebClient.Builder builder, @Value("${credit-card.tracking}") final String url) {
        this.client = builder
                .baseUrl(url)
                .build();
    }

    Mono<TrackingDTO> getTrackingOfCard(final int cardId) {
        return client.get()
                .uri(uriBuilder -> uriBuilder.path("/tracking")
                        .queryParam("cardId", cardId)
                        .build())
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
                .bodyToMono(TrackingDTO.class);
    }
}
