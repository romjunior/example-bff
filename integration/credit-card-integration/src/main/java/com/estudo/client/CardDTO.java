package com.estudo.client;

import java.time.LocalDate;

record CardDTO(int id, String cardNumber, String name, LocalDate expirationDate) {
}
