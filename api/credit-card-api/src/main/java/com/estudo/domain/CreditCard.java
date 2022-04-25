package com.estudo.domain;

import java.time.LocalDate;
import java.util.List;

public record CreditCard(int id, String cardNumber, String name, LocalDate expirationDate, String address, List<Step> steps) {

    public record Step(String address, LocalDate date) {

    }

}
