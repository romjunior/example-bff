package com.estudo.service;

import com.estudo.client.ListAllCreditCards;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class CreditCardServiceTest {

    private final CreditCardService underTest;

    private final ListAllCreditCards listAllCreditCards;

    public CreditCardServiceTest() {
        listAllCreditCards = Mockito.mock(ListAllCreditCards.class);
        this.underTest = new CreditCardService(listAllCreditCards);
    }

    @Test
    void shouldReturnEmptyList() {
        Mockito.when(listAllCreditCards.getAllCreditCards(1)).thenReturn(Flux.empty());
        StepVerifier.create(underTest.listAllCreditCardsFromUser(1))
                .expectNextCount(0)
                .verifyComplete();
    }
}
