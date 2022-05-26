package com.estudo.controller;

import com.estudo.domain.CreditCard;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

record CreditCardResponse(Integer id, String name, String cardNumber, LocalDate expirationDate, String address, @NonNull List<StepResponse> steps) {

    static CreditCardResponse of(final CreditCard creditCard) {
        return new CreditCardResponse(
                creditCard.id(),
                creditCard.name(),
                creditCard.cardNumber(),
                creditCard.expirationDate(),
                creditCard.address(),
                creditCard.steps().stream().map(step -> new StepResponse(step.address(), step.date())).toList());
    }

    record StepResponse(String address, LocalDate date) {

    }
}
