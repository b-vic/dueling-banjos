package com.duelingbanjos.demo.controller.client;

import com.duelingbanjos.demo.controller.BanjoOne;
import com.duelingbanjos.demo.model.Music;
import com.duelingbanjos.demo.repository.ResultRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("restclient")
public class RestClientBanjo extends BanjoOne {

    private final RestClient restClient;

    public RestClientBanjo(RestClient restClient, ResultRepository repository) {
        this.restClient = restClient;
        this.repository = repository;
    }

    @GetMapping
    public String playBanjoOne() {
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
