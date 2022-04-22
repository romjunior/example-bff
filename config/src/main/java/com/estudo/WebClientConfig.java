package com.estudo;

import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Slf4j
@Configuration
public class WebClientConfig {
    @Value("${spring.application.name}")
    private String appName;

    @Value("${application.timeout}")
    private String timeout;

    @Value("${application.connectTimeout}")
    private String connectTimeout;

    @Bean
    private WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ORIGIN, appName)
                .clientConnector(timeouts())
                .filter(logRequest());
    }
    private ClientHttpConnector timeouts() {
        return new ReactorClientHttpConnector(HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Integer.parseInt(connectTimeout))
                .responseTimeout(Duration.ofMillis(Long.parseLong(timeout))));
    }
    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request,M={} URL={}", clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }
}
