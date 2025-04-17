package com.duelingbanjos.demo.controller;

import com.duelingbanjos.demo.model.Music;
import com.duelingbanjos.demo.repository.ResultRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("resttemplate")
public class RestTemplateBanjo extends BanjoOne {

    private final RestTemplate restTemplate;

    public RestTemplateBanjo(RestTemplate restTemplate, ResultRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    @GetMapping
    public String playBanjo() {
        Music music = makeMusic();

        Music musicResponse = restTemplate
                .postForObject(
                        banjoTwoUrl,
                        music,
                        Music.class
                );

        return getResponseTime(this.getClass().getSimpleName(), musicResponse, true);
    }
}
