package com.estudo.controller;

import com.estudo.domain.CreditCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class CreditCardResponseTest {

    @Test
    void createByOf() {
        final var expected = new CreditCardResponse(
                1,
                "João Costa Henrique",
                "3233****43241",
                LocalDate.of(2024, 10, 2),
                "Rua Biro Biro",
                List.of(new CreditCardResponse.StepResponse("Rua Cheetos", LocalDate.of(2020, 10, 2)))
        );

        final var result = CreditCardResponse.of(new CreditCard(1,
                "3233****43241",
                "João Costa Henrique",
                LocalDate.of(2024, 10, 2),
                "Rua Biro Biro",
                List.of(new CreditCard.Step("Rua Cheetos", LocalDate.of(2020, 10, 2)))
        ));

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldThrowExceptionWhenStepIsNull() {
        Assertions.assertThrows(Exception.class, () -> {
            new CreditCardResponse(
                    1,
                    "João Costa Henrique",
                    "3233****43241",
                    LocalDate.of(2024, 10, 2),
                    "Rua Biro Biro",
                    null
            );
        });
    }


}
