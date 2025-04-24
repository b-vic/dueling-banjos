package com.duelingbanjos.demo.service;

import com.duelingbanjos.demo.model.Music;
import com.duelingbanjos.demo.repository.ResultRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientAsyncService extends BaseService {

    private final WebClient webClient;

    public WebClientAsyncService(WebClient webClient, ResultRepository repository) {
        this.webClient = webClient;
        this.resultRepository = repository;
    }

    public String playMusic() {
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

        //Don't save the response time as the real time is in the handleResponse method instead

        return getResponseTime(this.getClass().getSimpleName(), 0);
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
