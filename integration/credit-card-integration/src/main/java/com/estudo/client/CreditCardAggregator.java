package com.estudo.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
class CreditCardAggregator implements ListAllCreditCards {
    private final CreditCardClient creditCardClient;
    private final TrackingClient trackingClient;

    @Override
    public List<CreditCardDTO> getAllCreditCards(int userId) {
        return creditCardClient.getAllCards(userId)
                .stream()
                .peek(cardDTO -> log.info("M=getAllCreditCards retrive cardId={}", cardDTO.id()))
                .map(CreditCardDTO::of)
                .map(creditCardDTO -> creditCardDTO.addTracking(trackingClient.getTrackingOfCard(creditCardDTO.getId())))
                .toList();
    }
}
