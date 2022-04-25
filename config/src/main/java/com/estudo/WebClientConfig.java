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
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class WebClientConfig {
    @Value("${spring.application.name}")
    private String appName;

    @Value("${application.timeout}")
    private Integer timeout;

    @Value("${application.connectTimeout}")
    private Integer connectTimeout;

    @Value("${application.allow-headers-log}")
    private List<String> allowHeadersLog;

    @Bean
    WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ORIGIN, appName)
                .clientConnector(timeouts())
                .filter(logRequest());
    }
    private ClientHttpConnector timeouts() {
        return new ReactorClientHttpConnector(HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                .responseTimeout(Duration.ofMillis(timeout)));
    }
    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request,M={} Headers={} URL={}", clientRequest.method(), buildHeaders(clientRequest.headers()), buildUrl(clientRequest));
            return Mono.just(clientRequest);
        });
    }
    private String buildUrl(final ClientRequest clientRequest) {
        return clientRequest.url().getScheme() + "://" + clientRequest.url().getHost() + ":" + clientRequest.url().getPort() + clientRequest.url().getPath();
    }

    private String buildHeaders(final HttpHeaders headers) {
        return headers.keySet().stream()
                .filter(header -> allowHeadersLog.contains(header))
                .map(header -> header + "=" + headers.getOrEmpty(header).toString().replace("[", "").replace("]", ""))
                .collect(Collectors.joining(",", "[", "]"));
    }
}
