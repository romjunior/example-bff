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
                .map(cardDTO -> CreditCardDTO.of(
                        cardDTO.id(),
                        cardDTO.cardNumber(),
                        cardDTO.name(),
                        cardDTO.expirationDate()
                )).flatMap(creditCardDTO -> trackingClient.getTrackingOfCard(creditCardDTO.getId())
                        .map(trackingDTO -> addTracking(creditCardDTO, trackingDTO)));
    }

    CreditCardDTO addTracking(final CreditCardDTO dto, final TrackingDTO trackingDTO) {
        dto.setAddress(trackingDTO.address());
        trackingDTO.steps().forEach(stepDTO -> dto.addStep(stepDTO.address(), stepDTO.date()));
        return dto;
    }
}
