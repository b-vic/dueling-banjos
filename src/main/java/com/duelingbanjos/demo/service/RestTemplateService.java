package com.duelingbanjos.demo.service;

import com.duelingbanjos.demo.model.Music;
import com.duelingbanjos.demo.repository.ResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateService extends BaseService {

    private final RestTemplate restTemplate;

    public RestTemplateService(RestTemplate restTemplate, ResultRepository repository) {
        this.restTemplate = restTemplate;
        this.resultRepository = repository;
    }

    public String playMusic() {
        Music music = makeMusic();

        Music musicResponse = restTemplate
                .postForObject(
                        banjoTwoUrl,
                        music,
                        Music.class
                );

        double responseTime = saveResponseTime(musicResponse);

        return getResponseTime(this.getClass().getSimpleName(), responseTime);
    }
}
