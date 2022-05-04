package com.estudo.client;

import java.util.List;

public interface ListAllCreditCards {

    List<CreditCardDTO> getAllCreditCards(final int userId);
}
