package com.duelingbanjos.demo.controller;

import com.duelingbanjos.demo.model.Music;
import com.duelingbanjos.demo.repository.ResultRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("webclientasync")
public class WebClientBanjoAsync extends BanjoOne {

    private final WebClient webClient;

    public WebClientBanjoAsync(WebClient webClient, ResultRepository repository) {
        this.webClient = webClient;
        this.repository = repository;
    }

    @GetMapping
    public String playBanjo() {
        Music music = makeMusic();

        webClient.post()
                 .uri(banjoTwoUrl)
                 .contentType(MediaType.APPLICATION_JSON)
                 .bodyValue(music)
                 .retrieve()
                 .bodyToMono(Music.class)
                 .subscribe(
                         this::handleResponse,
                         this::handleError
                 );
        //Don't save the response time as the real time is in the handleResponse method
        return getResponseTime(this.getClass().getSimpleName(), music, false);
    }

    private void handleError(Throwable throwable) {
        System.err.println("Unexpected error: " + throwable.getMessage());
        throw new RuntimeException(throwable);
    }

    //Note this is the async call back so this is the more realistic result:
    private void handleResponse(Music response) {
        saveResponseTime(response);
    }

}
