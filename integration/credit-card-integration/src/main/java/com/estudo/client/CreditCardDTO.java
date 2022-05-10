package com.estudo.client;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreditCardDTO {

    private final CardDTO cardDTO;
    private TrackingDTO trackingDTO;

    public static CreditCardDTO of(final CardDTO cardDTO) {
        return new CreditCardDTO(cardDTO);
    }

    public CreditCardDTO addTracking(final TrackingDTO trackingDTO) {
        this.trackingDTO = trackingDTO;
        return this;
    }

    public int getId() {
        return cardDTO.id();
    }

    public String getName() {
        return cardDTO.name();
    }

    public String getCardNumber() {
        return cardDTO.cardNumber();
    }

    public LocalDate getExpirationDate() {
        return cardDTO.expirationDate();
    }

    public String getAddress() {
        return trackingDTO == null ? null : trackingDTO.address();
    }

    public List<CreditCardStep> getSteps() {
        return trackingDTO != null && trackingDTO.steps() != null ? trackingDTO.steps()
                .stream()
                .map(CreditCardStep::of)
                .toList() : List.of();
    }

    public record CreditCardStep(String address, LocalDate date) {
        static CreditCardStep of(final TrackingDTO.StepDTO stepDTO) {
            return new CreditCardStep(stepDTO.address(), stepDTO.date());
        }

    }
}
