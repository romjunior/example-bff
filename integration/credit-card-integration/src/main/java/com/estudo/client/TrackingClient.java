package com.estudo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "TrackingClient", url = "${credit-card.tracking}", decode404 = true)
interface TrackingClient {

    @GetMapping("/tracking")
    TrackingDTO getTrackingOfCard(@RequestParam("cardId") final Integer cardId);
}
