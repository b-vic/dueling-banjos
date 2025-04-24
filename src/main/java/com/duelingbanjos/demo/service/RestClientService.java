package com.duelingbanjos.demo.service;

import com.duelingbanjos.demo.model.Music;
import com.duelingbanjos.demo.repository.ResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RestClientService extends BaseService {

    private final RestClient restClient;

    public RestClientService(RestClient restClient, ResultRepository repository) {
        this.restClient = restClient;
        this.resultRepository = repository;
    }

    public String playMusic() {
        Music music = makeMusic();

        Music musicResponse =
                restClient.post()
                        .uri(banjoTwoUrl)
                        .body(music)
                        .retrieve()
                        .body(Music.class);

        double responseTime = saveResponseTime(musicResponse);

        return getResponseTime(this.getClass().getSimpleName(), responseTime);
    }
}
