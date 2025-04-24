package com.duelingbanjos.demo.controller;

import com.duelingbanjos.demo.service.RestClientService;
import com.duelingbanjos.demo.service.RestTemplateService;
import com.duelingbanjos.demo.service.WebClientAsyncService;
import com.duelingbanjos.demo.service.WebClientSyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BanjoOne {

    private final RestClientService restClientService;
    private final RestTemplateService restTemplateService;
    private final WebClientSyncService webClientSyncService;
    private final WebClientAsyncService webClientAsyncService;

    public BanjoOne(RestClientService restClientService, RestTemplateService restTemplateService, WebClientSyncService webClientSyncService, WebClientAsyncService webClientAsyncService) {
        this.restClientService = restClientService;
        this.restTemplateService = restTemplateService;
        this.webClientSyncService = webClientSyncService;
        this.webClientAsyncService = webClientAsyncService;
    }

    @GetMapping("resttemplate")
    public String playRestTemplateBanjo() {
        return restTemplateService.playMusic();
    }

    @GetMapping("restclient")
    public String playRestClientBanjo() {
        return restClientService.playMusic();
    }

    @GetMapping("webclientasync")
    public String playWebClientAsyncBanjo() {
        return webClientAsyncService.playMusic();
    }

    @GetMapping("webclientsync")
    public String playWebClientSyncBanjo() {
        return webClientSyncService.playMusic();
    }
}
