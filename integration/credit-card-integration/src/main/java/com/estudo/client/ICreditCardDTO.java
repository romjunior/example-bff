package com.estudo.client;

import java.time.LocalDate;
import java.util.List;

public interface ICreditCardDTO {

    int getId();
    String getName();
    String getCardNumber();
    LocalDate getExpirationDate();
    String getAddress();
    List<CreditCardStep> getSteps();
}
