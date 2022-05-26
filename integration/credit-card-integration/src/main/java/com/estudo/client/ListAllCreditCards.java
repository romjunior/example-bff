package com.estudo.client;

import reactor.core.publisher.Flux;

public interface ListAllCreditCards {

    Flux<ICreditCardDTO> getAllCreditCards(final int userId);
}
