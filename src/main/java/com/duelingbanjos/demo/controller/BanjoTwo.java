package com.duelingbanjos.demo.controller;

import com.duelingbanjos.demo.model.Music;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class BanjoTwo {

    public static final long MIN_SLEEP_TIME = 1;
    public static final long MAX_SLEEP_TIME = 50;

    private final Random random = new Random(System.currentTimeMillis());

    @PostMapping
    public Music playBanjoTwo(@RequestBody Music music) {
        Music responseMusic = new Music();
        long sleepTime = random.nextLong(MIN_SLEEP_TIME, MAX_SLEEP_TIME);
        try {
            //Banjo two sleeps to simulate external activity like a db call
            //This time will be subtracted from perf results since we send it back
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        responseMusic.setSleepTime(sleepTime);
        responseMusic.setStartTime(music.getStartTime());
        responseMusic.setId(music.getId());
        return responseMusic;
    }

}
