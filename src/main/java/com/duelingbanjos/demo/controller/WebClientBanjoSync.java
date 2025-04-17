package com.duelingbanjos.demo.controller;

import com.duelingbanjos.demo.model.Music;
import com.duelingbanjos.demo.repository.ResultRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("webclientsync")
public class WebClientBanjoSync extends BanjoOne {

    private final WebClient webClient;

    public WebClientBanjoSync(WebClient webClient, ResultRepository repository) {
        this.webClient = webClient;
        this.repository = repository;
    }

    @GetMapping
    public String playBanjo() throws ExecutionException, InterruptedException, TimeoutException {
        Music music = makeMusic();

        Music musicResponse = webClient.post()
                .uri(banjoTwoUrl)
                .bodyValue(music)
                .retrieve()
                .bodyToMono(Music.class)
                .block(); //makes our async client sync
        saveResponseTime(musicResponse);

        return getResponseTime(this.getClass().getSimpleName(), musicResponse, true);
    }

}
