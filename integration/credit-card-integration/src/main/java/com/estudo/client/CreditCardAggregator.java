package com.estudo.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@RequiredArgsConstructor
class CreditCardAggregator implements ListAllCreditCards {
    private final CreditCardClient creditCardClient;
    private final TrackingClient trackingClient;

    @Override
    public Flux<CreditCardDTO> getAllCreditCards(int userId) {
        return creditCardClient.getAllCards(userId)
                .doOnNext(cardDTO -> log.info("M=getAllCreditCards retrive cardId={}", cardDTO.id()))
                .map(CreditCardDTO::of)
                .flatMap(creditCard -> trackingClient
                        .getTrackingOfCard(creditCard.getId())
                        .doOnNext(trackingDTO -> log.info("M=getAllCreditCards retrieveTracking cardId={}", creditCard.getId()))
                        .map(creditCard::addTracking));
    }
}
