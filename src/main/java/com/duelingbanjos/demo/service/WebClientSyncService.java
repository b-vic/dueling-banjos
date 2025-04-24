package com.duelingbanjos.demo.service;

import com.duelingbanjos.demo.model.Music;
import com.duelingbanjos.demo.repository.ResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientSyncService extends BaseService {

    private final WebClient webClient;

    public WebClientSyncService(WebClient webClient, ResultRepository repository) {
        this.webClient = webClient;
        this.resultRepository = repository;
    }

    public String playMusic() {
        Music music = makeMusic();

        Music musicResponse = webClient.post()
                .uri(banjoTwoUrl)
                .bodyValue(music)
                .retrieve()
                .bodyToMono(Music.class)
                .block(); //makes our async client sync

        double responseTime = saveResponseTime(musicResponse);

        return getResponseTime(this.getClass().getSimpleName(), responseTime);
    }
}
