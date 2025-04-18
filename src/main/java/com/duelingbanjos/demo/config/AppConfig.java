package com.duelingbanjos.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }

    @Bean
    WebClient webClient() {
//Increase capacity as such:
//        ConnectionProvider provider = ConnectionProvider.builder("customProvider")
//                .pendingAcquireTimeout(Duration.ofSeconds(120))
//                .pendingAcquireMaxCount(2000)
//                .maxConnections(500)
//                .build();
//
//        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(HttpClient.create(provider))).build();
        return WebClient.builder().build();
    }

}
