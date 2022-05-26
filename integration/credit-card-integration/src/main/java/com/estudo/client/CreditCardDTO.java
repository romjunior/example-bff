package com.estudo.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreditCardDTO {

    private final int id;
    private final String cardNumber;
    private final String name;
    private final LocalDate expirationDate;
    @Setter
    private String address;
    private final List<CreditCardStep> steps;

    public static CreditCardDTO of(
            final int id,
            final String cardNumber,
            final String name,
            final LocalDate expirationDate
    ) {
        return new CreditCardDTO(id, cardNumber, name, expirationDate, List.of());
    }

    public void addStep(final String address, final LocalDate date) {
        steps.add(new CreditCardStep(address, date));
    }
    public record CreditCardStep(String address, LocalDate date) {

    }
}
