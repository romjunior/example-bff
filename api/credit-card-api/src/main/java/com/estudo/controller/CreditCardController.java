package com.estudo.controller;


import com.estudo.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService service;

    @GetMapping("/cards")
    public ResponseEntity<List<CreditCardResponse>> getAllCardsOfUser(@RequestParam(value = "userId") final Integer userId) {
        log.info("M=getAllCardsOfUser requested userId={}", userId);
        var card = service.listAllCreditCardsFromUser(userId)
                .stream()
                .map(CreditCardResponse::of)
                .toList();
        log.info("M=geAllCardsOfUser success userId={}", userId);
        return card.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(card);
    }
}
