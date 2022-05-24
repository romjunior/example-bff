package com.estudo.controller;


import com.estudo.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService service;

    @GetMapping("/cards")
    public Flux<CreditCardResponse> getAllCardsOfUser(@RequestParam(value = "userId") final Integer userId) {
        return Flux.just(userId)
                .doOnNext(id -> log.info("M=getAllCardsOfUser requested userId={}", id))
                .flatMap(service::listAllCreditCardsFromUser)
                .map(CreditCardResponse::of)
                .doOnComplete(() -> log.info("M=geAllCardsOfUser success userId={}", userId));
    }
}
