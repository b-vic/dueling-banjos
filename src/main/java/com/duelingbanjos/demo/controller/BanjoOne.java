package com.duelingbanjos.demo.controller;

import com.duelingbanjos.demo.entity.Result;
import com.duelingbanjos.demo.model.Music;
import com.duelingbanjos.demo.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public abstract class BanjoOne {

    @Value("http://127.0.0.1:8081")
    protected String banjoTwoUrl; //ideally this is running on a separate server

    protected ResultRepository repository;

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
        Result result = new Result();
        result.setId(music.getId().toString());
        result.setType(this.getClass().getSimpleName());
        result.setResponseTime(getResponseTime(music));
        return repository.save(result).getResponseTime();
    }

    private static double getResponseTime(Music music) {
        return (System.nanoTime() - music.getStartTime()) / 1_000_000.0 - music.getSleepTime();
    }

}
