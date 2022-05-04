package com.estudo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "CreditCardClient", url = "${credit-card.url}", decode404 = true)
interface CreditCardClient {

    @GetMapping(value = "/card", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<CardDTO> getAllCards(@RequestParam("userId") final Integer userId);
}
