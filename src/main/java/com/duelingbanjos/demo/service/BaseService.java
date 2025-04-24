package com.duelingbanjos.demo.service;

import com.duelingbanjos.demo.entity.PerfResult;
import com.duelingbanjos.demo.model.Music;
import com.duelingbanjos.demo.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.UUID;

public class BaseService {

    @Value("http://127.0.0.1:8081")
    protected String banjoTwoUrl; //ideally this is running on a separate server

    protected ResultRepository resultRepository;

    public Music makeMusic() {
        long startTime = System.nanoTime();
        UUID uuid = UUID.randomUUID();
        Music music = new Music();
        music.setStartTime(startTime);
        music.setId(uuid);
        return music;
    }

    protected String getResponseTime(String banjoType, double responseTime) {
        return banjoType + "," + responseTime;
    }

    protected double saveResponseTime(Music music) {
        PerfResult perfResult = new PerfResult();
        perfResult.setId(music.getId().toString());
        perfResult.setType(this.getClass().getSimpleName());
        perfResult.setResponseTime(getResponseTime(music));
        perfResult.setCreationTime(LocalDateTime.now());
        return resultRepository.save(perfResult).getResponseTime();
    }

    private static double getResponseTime(Music music) {
        return (System.nanoTime() - music.getStartTime()) / 1_000_000.0 - music.getSleepTime();
    }
}
