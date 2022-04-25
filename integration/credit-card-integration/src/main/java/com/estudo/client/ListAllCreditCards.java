package com.estudo.client;

import reactor.core.publisher.Flux;

public interface ListAllCreditCards {

    Flux<CreditCardDTO> getAllCreditCards(final int userId);
}
