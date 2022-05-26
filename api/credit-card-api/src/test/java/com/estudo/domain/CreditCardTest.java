package com.estudo.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class CreditCardTest {

    @Test
    void shouldThrowExceptionWhenStepIsNull() {
        Assertions.assertThrows(Exception.class, () -> {
            new CreditCard(1,
                    "3233****43241",
                    "João Costa Henrique",
                    LocalDate.of(2024, 10, 2),
                    "Rua Biro Biro",
                    null
            );
        });
    }
}
