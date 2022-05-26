package com.estudo.service;

import com.estudo.client.CreditCardStep;
import com.estudo.client.ICreditCardDTO;
import com.estudo.client.ListAllCreditCards;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

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

    void shouldReturnWithCreditCard() {
        Mockito.when(listAllCreditCards.getAllCreditCards(2)).thenReturn(Flux.just(
                new ICreditCardDTO() {
                    @Override
                    public int getId() {
                        return 0;
                    }

                    @Override
                    public String getName() {
                        return "John";
                    }

                    @Override
                    public String getCardNumber() {
                        return "333";
                    }

                    @Override
                    public LocalDate getExpirationDate() {
                        return LocalDate.now();
                    }

                    @Override
                    public String getAddress() {
                        return "Address";
                    }

                    @Override
                    public List<CreditCardStep> getSteps() {
                        return List.of();
                    }
                }
        ));

        StepVerifier.create(underTest.listAllCreditCardsFromUser(1))
                .expectNextCount(1)
                .verifyComplete();
    }
}
